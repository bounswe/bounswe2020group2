package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentSubcategoryBinding
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.adapters.SubCategoryAdapter
import com.example.getflix.ui.fragments.SubcategoryFragmentDirections.Companion.actionSubcategoryFragmentToFilterOptionsFragment
import com.example.getflix.ui.viewmodels.SubCategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.fragment_subcategory.*


class SubcategoryFragment : Fragment() {

    private lateinit var viewModel: SubCategoryViewModel
    private var filter = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSubcategoryBinding>(
                inflater, R.layout.fragment_subcategory,
                container, false
        )

        activity?.toolbar!!.visibility = View.GONE
        val navController = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = ""
        val args = SubcategoryFragmentArgs.fromBundle(requireArguments())
        val subCat = args.subName
        binding.toolbarTitle.text = subCat
        viewModel = ViewModelProvider(this).get(SubCategoryViewModel::class.java)
        //binding.viewmodel = SubCategoryViewModel()
        val recView = binding?.productList as RecyclerView
        var zaraJacket1 =
                ProductModel(10, "Jacket", "2", "1", "Zara", 1, 1, 1, "Nice jacket", "1", "1", "1", "1")
        var zaraJacket2 =
                ProductModel(11, "Jacket", "31", "1", "Zara", 1, 1, 1, "Cool jacket", "1", "1", "1", "1")
        var zaraJacket3 =
                ProductModel( 12,"Jacket","3","1","Zara",1,1,1,"Amazing jacket","1","1","1","1")
        var zaraSkirt1 =
                ProductModel(4, "Skirt", "79", "1", "Zara", 1, 1, 1, "Nice skirt", "1", "1", "1", "1")
        var zaraSkirt2 =
                ProductModel(5, "Skirt", "93", "1", "Zara", 1, 1, 1, "Cool skirt", "1", "1", "1", "1")
        var zaraSkirt3 =
                ProductModel(6, "Skirt", "102", "1", "Zara", 1, 1, 1, "Amazing skirt", "1", "1", "1", "1")
        val products = arrayListOf( zaraSkirt3,zaraJacket1,zaraSkirt2,zaraJacket2,zaraSkirt1,zaraJacket3)
        val manager = GridLayoutManager(activity, 2)
        recView.layoutManager = manager
        val productListAdapter = SubCategoryAdapter(products)
        recView.adapter = productListAdapter
        recView.setHasFixedSize(true)

        for(product in products) {
            viewModel.addProduct(product)
        }

        binding.btnFilter.setOnClickListener {
            filter = true
            view?.findNavController()!!.navigate(actionSubcategoryFragmentToFilterOptionsFragment())
        }

        viewModel.productList.observe(viewLifecycleOwner,{
            it?.let {
                productListAdapter.submitList(it)
            }
        })

        val sorts = resources.getStringArray(R.array.SortBy)
        val spinner = binding.spinner
        val adapter = ArrayAdapter(activity?.applicationContext!!, android.R.layout.simple_spinner_dropdown_item, sorts)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.sort(parent?.getItemAtPosition(position).toString())

            }

        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if(!filter)
        activity?.toolbar!!.visibility = View.VISIBLE
    }
}