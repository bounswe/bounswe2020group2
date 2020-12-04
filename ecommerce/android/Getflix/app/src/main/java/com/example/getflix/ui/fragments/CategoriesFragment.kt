package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.ui.adapters.SubcategoryAdapter
import com.example.getflix.databinding.FragmentCategoriesBinding
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.PModel
import com.example.getflix.models.SubcategoryModel
import com.example.getflix.ui.adapters.CategoriesAdapter
import com.example.getflix.ui.viewmodels.CategoriesViewModel
import com.example.getflix.ui.viewmodels.CategoryViewModel
import com.example.getflix.ui.viewmodels.HomeViewModel

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
        binding.lifecycleOwner=this

        var catsL = mutableListOf<CategoryModel>()
        var cats = listOf<CategoryModel>(
                CategoryModel(
                        "Home",
                        listOf(SubcategoryModel("Furniture", null)) as MutableList<SubcategoryModel>
                ), CategoryModel(
                "Electronics",
                listOf(SubcategoryModel( "Computers",null),
                        SubcategoryModel("Mobile Phones",null)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Baby",
                listOf(SubcategoryModel("Baby Food", null)) as MutableList<SubcategoryModel>
        )
        )
        adapter = CategoriesAdapter(cats, this)
        binding.catRec.adapter = adapter

        //viewModel.getProducts(3)
        //viewModel.getProduct(3)
        //viewModel.getUserCartProducts(2)

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







        /*for(category in cats) {
            viewModel.addCategory(category)
        } */

        /*viewModel.categoriesList.observe(viewLifecycleOwner, {
            it?.let {
                adapter = CategoriesAdapter(it, this)
                binding.catRec.adapter = adapter
            }
        }) */



        categoryViewModel =  ViewModelProvider(this).get(CategoryViewModel::class.java)


        val adapter = SubcategoryAdapter(requireContext())
        categoryViewModel.displayedCategory.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it["woman"])
            }
        })


        return binding.root
    }



}

