package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentVendorOrdersBinding
import com.example.getflix.ui.adapters.VendorOrderAdapter
import com.example.getflix.ui.viewmodels.VendorOrdersViewModel
import com.example.getflix.vendorOrderModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class VendorOrdersFragment : Fragment() {

    private lateinit var binding: FragmentVendorOrdersBinding
    private lateinit var vendorOrdersViewModel: VendorOrdersViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.toolbar!!.toolbar_title.text = getString(R.string.orders_vendor)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_vendor_orders,
            container, false
        )
        vendorOrdersViewModel = ViewModelProvider(this).get(VendorOrdersViewModel::class.java)
        binding.lifecycleOwner = this


        val vendorOrderAdapter = VendorOrderAdapter(requireContext())
        binding.ordersList.adapter = vendorOrderAdapter
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.ordersList.layoutManager = layoutManager


        vendorOrderAdapter.selectedStatus.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                println(it)
                println(vendorOrderAdapter.selectedOrder.value.toString())
                vendorOrdersViewModel.changeStatusOfOrder(
                    vendorOrderAdapter.selectedOrder.value!!,
                    it,
                    requireContext()
                )
                vendorOrderAdapter.onSelectionCompleted()
            }
        })

        vendorOrdersViewModel.orders.observe(viewLifecycleOwner, Observer {
            vendorOrderAdapter.submitList(it)
        })
        vendorOrderAdapter.destination.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                vendorOrderModel = it!!
                findNavController().navigate(
                    VendorOrdersFragmentDirections.actionVendorOrdersFragmentToVendorOrderFragment(),
                    vendorOrderAdapter.fragmentNavigatorExtras!!
                )
            }
            })

        return binding.root
    }


}