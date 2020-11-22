package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.ui.adapters.SubcategoryAdapter
import com.example.getflix.databinding.FragmentCategoriesBinding
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.SubcategoryModel
import com.example.getflix.ui.adapters.CategoriesAdapter
import com.example.getflix.ui.viewmodels.CategoriesViewModel
import com.example.getflix.ui.viewmodels.CategoryViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


    private lateinit var viewModel: CategoriesViewModel
    private lateinit var adapter: CategoriesAdapter

class CategoriesFragment : Fragment() {

    lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCategoriesBinding>(
            inflater, R.layout.fragment_categories,
            container, false
        )

        activity?.toolbar!!.toolbar_title.text = getString(R.string.categories)
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        binding.viewmodel = CategoriesViewModel()


        var cats = listOf<CategoryModel>(
            CategoryModel(
                1, "Home",
                listOf(SubcategoryModel(2, 1, "Furniture",null))
            ), CategoryModel(
                2, "Electronics",
                listOf(SubcategoryModel(2, 2, "Computers",null),
                    SubcategoryModel(3, 2, "Mobile Phones",null))
            ), CategoryModel(
                3, "Baby",
                listOf(SubcategoryModel(2, 2, "Baby Food",null))
            )
        )


        for(category in cats) {
            viewModel.addCategory(category)
        }

        viewModel.categoriesList.observe(viewLifecycleOwner, {
            it?.let {
                adapter = CategoriesAdapter(it, this)
                binding.catRec.adapter = adapter
            }
        })



        categoryViewModel =  ViewModelProvider(this).get(CategoryViewModel::class.java)


        val adapter = SubcategoryAdapter(requireContext())
        categoryViewModel.displayedCategory.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it["woman"])
            }
        })


        return binding.root
    }

    fun navigateSub() {
        val subf = SubcategoryFragment()
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.my_nav_host_fragment,subf)
            addToBackStack("")
            commit()
        }
        //view?.findNavController()?.navigate(actionCategoriesFragmentToSubcategoryFragment())
        //NavHostFragment.findNavController(this).navigate(R.id.action_categoriesFragment_to_subcategoryFragment)
    }

}

