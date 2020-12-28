package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCartBinding

import com.example.getflix.models.*

import com.example.getflix.models.CartProductModel

import com.example.getflix.ui.adapters.CartAdapter
import com.example.getflix.ui.adapters.SwipeToDeleteAddress
import com.example.getflix.ui.adapters.SwipeToDeleteCartProduct
import com.example.getflix.ui.fragments.CartFragmentDirections.Companion.actionCartFragmentToCompleteOrderFragment
import com.example.getflix.ui.viewmodels.CartViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCartBinding>(inflater, R.layout.fragment_cart,
                container, false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.cart)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        viewModel.getCustomerCartProducts()
        viewModel.getCustomerCartPrice()

        val recView = binding?.cartList as RecyclerView


        binding.acceptOrder.setOnClickListener {
            activity?.loading_progress!!.visibility = View.VISIBLE
            view?.findNavController()?.navigate(actionCartFragmentToCompleteOrderFragment())
        }


        val productListAdapter = CartAdapter(this, viewModel)
        val layoutManager =  LinearLayoutManager(activity)
        recView.adapter = productListAdapter
        recView.layoutManager = layoutManager
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCartProduct(productListAdapter))
        itemTouchHelper.attachToRecyclerView(recView)

        viewModel.cardPrices.observe(viewLifecycleOwner, Observer{
            binding.productsPrice.text = "Products Price: " + it.productsPrice.toString()
            binding.deliveryPrice.text = "Delivery Price: " + it.deliveryPrice.toString()
            binding.discount.text = "Discount: " + it.discount.toString()
            binding.totalPrice.text = "Total Price: " + it.totalPrice.toString()
        })



        productListAdapter.pos.observe(viewLifecycleOwner, Observer {
            if (it != -1) {
                val id = productListAdapter.deleteItem(it).id
                viewModel.deleteCustomerCartProduct(id)
                productListAdapter.submitList(viewModel.cardProducts.value)
                productListAdapter.resetPos()
            }
        })
        viewModel.onSubmit.observe(viewLifecycleOwner, Observer {
            if (it) {
                productListAdapter.submitList(viewModel.cardProducts.value)
            }

        })
        /*viewModel.productList.observe(viewLifecycleOwner, Observer {
            it?.let {
                productListAdapter.submitList(it)
            }
        }) */

        return binding.root
    }


}
