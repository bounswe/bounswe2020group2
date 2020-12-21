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
import com.example.getflix.ui.adapters.CategoriesAdapter
import com.example.getflix.ui.adapters.SubcategoryHorizontalAdapter
import com.example.getflix.ui.viewmodels.CategoriesViewModel
import com.example.getflix.ui.viewmodels.CategoryViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class CategoriesFragment : Fragment() {

    lateinit var categoryViewModel: CategoryViewModel
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var adapter: CategoriesAdapter

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
        binding.lifecycleOwner = this

        var catsL = mutableListOf<CategoryModel>()
        var cats = listOf<CategoryModel>(
                CategoryModel(
                        "Electronics",
                        1,listOf(SubcategoryModel("Computers", 1),
                                SubcategoryModel("Camera & Photo", 1),
                                SubcategoryModel("Cell Phones & Accessories", 1),
                                SubcategoryModel("Digital Videos", 1),
                                SubcategoryModel("Software", 1)) as MutableList<SubcategoryModel>
                ), CategoryModel(
                "Health & Households",1,
                listOf(SubcategoryModel("Sports & Outdoor", 1),
                        SubcategoryModel("Beauty & Personal Care", 1)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Home & Garden",1,
                listOf(SubcategoryModel("Luggage", 1),
                        SubcategoryModel("Pet Supplies", 1),
                        SubcategoryModel("Furniture", 1)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Clothing",1,
                listOf(SubcategoryModel("Men's Fashion", 1),
                        SubcategoryModel("Women's Fashion", 1),
                        SubcategoryModel("Boys' Fashion", 1),
                        SubcategoryModel("Girls' Fashion", 1),
                        SubcategoryModel("Baby", 1)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Hobbies",1,
                listOf(SubcategoryModel("Books", 1),
                        SubcategoryModel("Music & CDs", 1),
                        SubcategoryModel("Movies & TVs", 1),
                        SubcategoryModel("Toys & Games", 1),
                        SubcategoryModel("Video Games", 1),
                        SubcategoryModel("Arts & Crafts", 1)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Others",1,
                listOf(SubcategoryModel("Automotive", 1),
                        SubcategoryModel("Industrial & Scientific", 1)) as MutableList<SubcategoryModel>
        )
        )
        adapter = CategoriesAdapter(cats, this)
        binding.catRec.adapter = adapter
        //viewModel.getProducts(3)
        //viewModel.getProduct(3)
        //viewModel.addToCart(1,4)
        //viewModel.getUserCartProducts(20)

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

