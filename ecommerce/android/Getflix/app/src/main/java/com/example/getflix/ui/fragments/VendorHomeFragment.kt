package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.databinding.FragmentVendorHomeBinding
import com.example.getflix.databinding.FragmentVendorProfileBinding
import com.example.getflix.ui.viewmodels.VendorHomeViewModel
import com.example.getflix.ui.viewmodels.VendorOrdersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class VendorHomeFragment : Fragment() {

    private lateinit var binding: FragmentVendorHomeBinding
    private lateinit var viewModel: VendorHomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vendor_home,
                container, false)

        activity?.bottom_nav!!.visibility = View.VISIBLE
        activity?.toolbar_lay!!.visibility = View.VISIBLE
        activity?.toolbar!!.toolbar_title.text = getString(R.string.home)
        activity?.toolbar!!.btn_notification.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this).get(VendorHomeViewModel::class.java)

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        activity?.toolbar!!.btn_notification.visibility = View.GONE
    }


}