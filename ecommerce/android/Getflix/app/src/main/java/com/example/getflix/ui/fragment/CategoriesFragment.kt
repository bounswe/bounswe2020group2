package com.example.getflix.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.adapters.SubcategoryAdapter
import com.example.getflix.databinding.FragmentCategoriesBinding

import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener
/* import com.example.getflix.model.CategoryModel
import com.example.getflix.model.SubcategoryModel
import com.example.getflix.ui.adapter.CategoriesAdapter
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.* */





class CategoriesFragment : Fragment() {
    private lateinit var categoriesViewModel: CategoriesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCategoriesBinding>(
            inflater, R.layout.fragment_categories,
            container, false
        )

      /*  activity?.toolbar!!.toolbar_title.text = getString(R.string.categories)

        var cats = listOf<CategoryModel>(
            CategoryModel(
                1, "Home",
                listOf(SubcategoryModel(2, 1, "Furniture"))
            ), CategoryModel(
                2, "Electronics",
                listOf(SubcategoryModel(2, 2, "Computers"),
                    SubcategoryModel(3, 2, "Mobile Phones"))
            ), CategoryModel(
                3, "Baby",
                listOf(SubcategoryModel(2, 2, "Baby Food"))
            )
        )
        //var catss = listOf(CategoryModel(1,cats[0].name, cats[0].subCats))
        val adapter = CategoriesAdapter(cats)
        binding.catRec.adapter = adapter */


        categoriesViewModel =  ViewModelProvider(this).get(CategoriesViewModel::class.java)
        val adapter = SubcategoryAdapter()
        categoriesViewModel.displayedCategories.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it["woman"])
            }
        })
        return binding.root
    }


}

