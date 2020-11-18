package com.example.getflix.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCartBinding
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.adapters.CartAdapter
import com.example.getflix.ui.adapters.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class CartFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCartBinding>(inflater,R.layout.fragment_cart,
            container,false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.cart)

        val recView = binding?.cartList as RecyclerView
        val products = arrayListOf(
            ProductModel(1,"iPhone","Electronics",null,null,null),
            ProductModel(1,"Bag","Electronics",null,null,null)
        )
        val productListAdapter = CartAdapter(products)
        recView.adapter = productListAdapter
        recView.setHasFixedSize(true)

        return binding.root
    }




}