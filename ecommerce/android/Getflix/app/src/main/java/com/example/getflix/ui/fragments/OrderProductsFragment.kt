package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getflix.R


class OrderProductsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentOrderProductsBinding>(
            inflater, R.layout.fragment_order_products,
            container, false
        )
        activity?.toolbar!!.toolbar_title.text = getString(R.string.order_products)
        viewModel = ViewModelProvider(this).get(OrderProductViewModel::class.java)
        val recView = binding?.listProductList as RecyclerView
    }

}