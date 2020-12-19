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
                        listOf(SubcategoryModel("Computers", null),
                                SubcategoryModel("Camera & Photo", null),
                                SubcategoryModel("Cell Phones & Accessories", null),
                                SubcategoryModel("Digital Videos", null),
                                SubcategoryModel("Software", null)) as MutableList<SubcategoryModel>
                ), CategoryModel(
                "Health & Households",
                listOf(SubcategoryModel("Sports & Outdoor", null),
                        SubcategoryModel("Beauty & Personal Care", null)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Home & Garden",
                listOf(SubcategoryModel("Luggage", null),
                        SubcategoryModel("Pet Supplies", null),
                        SubcategoryModel("Furniture", null)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Clothing",
                listOf(SubcategoryModel("Men's Fashion", null),
                        SubcategoryModel("Women's Fashion", null),
                        SubcategoryModel("Boys' Fashion", null),
                        SubcategoryModel("Girls' Fashion", null),
                        SubcategoryModel("Baby", null)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Hobbies",
                listOf(SubcategoryModel("Books", null),
                        SubcategoryModel("Music & CDs", null),
                        SubcategoryModel("Movies & TVs", null),
                        SubcategoryModel("Toys & Games", null),
                        SubcategoryModel("Video Games", null),
                        SubcategoryModel("Arts & Crafts", null)) as MutableList<SubcategoryModel>
        ), CategoryModel(
                "Others",
                listOf(SubcategoryModel("Automotive", null),
                        SubcategoryModel("Industrial & Scientific", null)) as MutableList<SubcategoryModel>
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

