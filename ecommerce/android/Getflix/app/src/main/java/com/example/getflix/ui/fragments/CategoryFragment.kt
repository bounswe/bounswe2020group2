package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.ui.adapters.SubcategoryAdapter
import com.example.getflix.databinding.FragmentCategoryBinding
import com.example.getflix.models.PModel
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.viewmodels.CategoryViewModel


class CategoryFragment : Fragment() {
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var adapter: SubcategoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCategoryBinding>(
            inflater, R.layout.fragment_category,
            container, false
        )
        val categoryId = CategoryFragmentArgs.fromBundle(requireArguments()).categoryId

        val args = arguments
        var myList: ArrayList<PModel> =
            args!!.getParcelableArrayList<PModel>("Product") as ArrayList<PModel>
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        val adapter = SubcategoryAdapter(requireContext())
        categoryViewModel.setCategory(categoryId)

        binding.lifecycleOwner = this
        binding.categoryList.adapter = adapter
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.categoryList.layoutManager = layoutManager

        categoryViewModel.displayedCategory.observe(viewLifecycleOwner, Observer {
            it.let {
                println(it.subCats.size)
                adapter.submitList(it.subCats)
            }
        })

        // val categoryId = CategoryFragmentArgs.fromBundle(requireArguments()).categoryId
        // categoryViewModel.setCategory(categoryId)
        return binding.root
    }
}
