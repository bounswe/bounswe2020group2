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
import com.example.getflix.databinding.FragmentCategoriesBinding
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.SubcategoryModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.ui.adapters.CategoriesAdapter
import com.example.getflix.ui.adapters.SubcategoryHorizontalAdapter
import com.example.getflix.ui.viewmodels.CategoriesViewModel
import com.example.getflix.ui.viewmodels.CategoryViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*


class CategoriesFragment : Fragment() {

    lateinit var categoryViewModel: CategoryViewModel
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var adapter: CategoriesAdapter
    var cats1 = arrayListOf<CategoryModel>()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.loading_progress!!.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        viewModel.getCategories()

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCategoriesBinding>(
                inflater, R.layout.fragment_categories,
                container, false
        )

        activity?.toolbar!!.toolbar_title.text = getString(R.string.categories)
        binding.lifecycleOwner = this

       var cats1 = arrayListOf<CategoryModel>()


       viewModel.categoriess.observe(viewLifecycleOwner, {
           it?.let {
               for(category in it.categories!!) {
                   var name = category.name
                   var id = category.id
                   var sub: MutableList<SubcategoryModel> = category.subcategories
                   cats1.add(CategoryModel(name,id,sub))
               }
               adapter = CategoriesAdapter(cats1, this)
               binding.catRec.adapter = adapter
               activity?.loading_progress!!.visibility = View.GONE
           }
       })

        /*viewModel.products?.observe(viewLifecycleOwner, {products ->
            products?.let {
               viewModel.setCategories(it as MutableList<PModel>)
                viewModel.categoriesList?.observe(viewLifecycleOwner, {cats ->
                    cats?.let {
                        catsL = cats
                        println(catsL.size)
                        adapter.notifyDataSetChanged()
                    }
            }
                )
            }}) */

        //val cat = viewModel.categories





        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)


        val adapter = SubcategoryHorizontalAdapter(requireContext())
        categoryViewModel.displayedCategory.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it["woman"])
            }
        })


        return binding.root
    }





}

