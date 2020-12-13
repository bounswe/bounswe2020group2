package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCartBinding
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.adapters.CartAdapter
import com.example.getflix.ui.viewmodels.CartViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCartBinding>(inflater,R.layout.fragment_cart,
            container,false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.cart)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        binding.viewmodel = CartViewModel()
        val recView = binding?.cartList as RecyclerView
        var zaraJacket1 =
                ProductModel(10, "Jacket", "222", "1", "Zara", 1, 1, 1, "Nice jacket", "1", "1", "1", "1")
        var zaraJacket2 =
                ProductModel(11, "Jacket", "231", "1", "Zara", 1, 1, 1, "Cool jacket", "1", "1", "1", "1")
        var zaraJacket3 =
                ProductModel( 12,"Jacket","32","1","Zara",1,1,1,"cool","Amazing jacket","1","1","1")
        var zaraSkirt1 =
                ProductModel(4, "Skirt", "79", "1", "Zara", 1, 1, 1, "Nice skirt", "1", "1", "1", "1")
        var zaraSkirt2 =
                ProductModel(5, "Skirt", "93", "1", "Zara", 1, 1, 1, "Cool skirt", "1", "1", "1", "1")
        var zaraSkirt3 =
                ProductModel(6, "Skirt", "102", "1", "Zara", 1, 1, 1, "Amazing skirt", "1", "1", "1", "1")
        val products = arrayListOf( zaraSkirt3,zaraJacket1,zaraSkirt2,zaraJacket2,zaraSkirt1,zaraJacket3)
        val productListAdapter = CartAdapter(products)
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




}
