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
import com.example.getflix.ui.viewmodels.VendorOrdersViewModel
import com.example.getflix.ui.viewmodels.VendorProfileViewModel
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

        return binding.root
    }


}