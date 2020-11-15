package com.example.getflix.ui.fragment

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
import com.example.getflix.adapters.SubcategoryAdapter
import com.example.getflix.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var adapter: SubcategoryAdapter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCategoryBinding>(inflater, R.layout.fragment_category,
                container, false)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        val adapter = SubcategoryAdapter(requireContext())
        categoryViewModel.displayedCategory.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it.subCats)
            }
        })
        binding.lifecycleOwner = this
        binding.categoryList.adapter = adapter
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.categoryList.layoutManager = layoutManager
        categoryViewModel.setCategory(1)

        return binding.root
    }
}
