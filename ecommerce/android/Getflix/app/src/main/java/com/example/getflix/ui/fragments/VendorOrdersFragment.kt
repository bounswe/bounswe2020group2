package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.databinding.FragmentVendorOrdersBinding
import com.example.getflix.models.OrderModel
import com.example.getflix.ui.adapters.VendorOrdersAdapter
import com.example.getflix.ui.viewmodels.VendorOrdersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class VendorOrdersFragment : Fragment() {

    private lateinit var binding: FragmentVendorOrdersBinding
    private lateinit var viewModel: VendorOrdersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        activity?.toolbar!!.toolbar_title.text = getString(R.string.orders_vendor)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vendor_orders,
                container, false)
        viewModel = ViewModelProvider(this).get(VendorOrdersViewModel::class.java)
        binding.lifecycleOwner = this

        val recView = binding.ordersList
        var orders = arrayListOf<OrderModel>()


        val order1 = OrderModel(1,3,5.0,15.0,"accepted","23.12.2020",null,
            null)
        val order2 = OrderModel(2,4,5.0,20.0,"accepted","14.12.2020",null,
            null)
        val order3 = OrderModel(3,2,5.0,10.0,"accepted","13.12.2020",null,
            null)
        val order4 = OrderModel(4,1,5.0,5.0,"accepted","21.12.2020",null,
            null)

        viewModel.addOrder(order1)
        viewModel.addOrder(order2)
        viewModel.addOrder(order3)
        viewModel.addOrder(order4)

        val orderListAdapter = VendorOrdersAdapter(null)
        recView.adapter = orderListAdapter
        recView.setHasFixedSize(true)

        viewModel.orderList.observe(viewLifecycleOwner,  {
            it?.let {
                val orderListAdapter = VendorOrdersAdapter(it!!)
                recView.adapter = orderListAdapter
                recView.setHasFixedSize(true)
                orderListAdapter.submitList(it)
            }
        })

        return binding.root
    }


}