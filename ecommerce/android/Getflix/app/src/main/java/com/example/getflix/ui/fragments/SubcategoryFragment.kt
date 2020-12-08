package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentSubcategoryBinding
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.adapters.SubCategoryAdapter
import com.example.getflix.ui.viewmodels.SubCategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class SubcategoryFragment : Fragment() {

    private lateinit var viewModel: SubCategoryViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSubcategoryBinding>(
                inflater, R.layout.fragment_subcategory,
                container, false
        )

        activity?.toolbar!!.visibility = View.GONE
        val navController =
                Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = ""
        val args = SubcategoryFragmentArgs.fromBundle(requireArguments())
        val subCat = args.subName
        binding.toolbarTitle.text = subCat
        viewModel = ViewModelProvider(this).get(SubCategoryViewModel::class.java)
        binding.viewmodel = SubCategoryViewModel()
        val recView = binding?.productList as RecyclerView
        val products = arrayListOf( ProductModel(1,"Bag","100 TL",null,"Vıntage Bag"),
                ProductModel(1,"iPhone 7","4815 TL",null,"Best Phone"),
                ProductModel(1,"Pullover","36 TL",null,"Black Pullover"),
                ProductModel(1,"Notebook","32 TL",null,"Spiral Notebook"),
                ProductModel(1,"Pencil","13 TL",null,"Black Pencil"),
                ProductModel(1,"Skirt","30 TL",null,"Vıntage Skirt"),
                ProductModel(1,"T-Shirt","23 TL",null,"Vıntage T-Shirt"),
                ProductModel(1,"Book","20 TL",null,"Bestseller Book")   ,
                ProductModel(1,"T-Shirt","23 TL",null,"Black T-Shirt"),
                ProductModel(1,"Book","20 TL",null,"Bestseller Book"))
        val productListAdapter = SubCategoryAdapter(products)
        recView.adapter = productListAdapter
        recView.setHasFixedSize(true)

        for(product in products) {
            viewModel.addProduct(product)
        }

        viewModel.productList.observe(viewLifecycleOwner, Observer{
            it?.let {
                productListAdapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        activity?.toolbar!!.visibility = View.VISIBLE
    }


}