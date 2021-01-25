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
import com.example.getflix.databinding.FragmentOrderProductsBinding
import com.example.getflix.models.*
import com.example.getflix.ui.adapters.OrderProductsAdapter
import com.example.getflix.ui.viewmodels.OrderPurchasedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class OrderProductsFragment : Fragment() {

    private lateinit var viewModel: OrderPurchasedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentOrderProductsBinding>(
            inflater, R.layout.fragment_order_products,
            container, false
        )
        activity?.toolbar!!.toolbar_title.text = getString(R.string.order_products)

        viewModel = ViewModelProvider(this).get(OrderPurchasedViewModel::class.java)
        val recView = binding?.listProductList as RecyclerView
        val args = OrderProductsFragmentArgs.fromBundle(requireArguments())
        val productList = args.products.toCollection(ArrayList())
        println(productList.toString())


        val listAdapter = OrderProductsAdapter(productList)
        recView.adapter = listAdapter
        recView.setHasFixedSize(true)

        for (product in productList) {
            viewModel.addOrderPurchased(product)
        }

        viewModel.purchasedProductList.observe(viewLifecycleOwner, Observer {
            it?.let {
                listAdapter.submitList(it)
            }
        })


        return binding.root
    }

}