package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getflix.R
import com.example.getflix.databinding.FragmentVendorReviewsBinding
import com.example.getflix.ui.adapters.VendorCommentAdapter
import com.example.getflix.ui.viewmodels.VendorReviewsViewModel
import kotlinx.android.synthetic.main.activity_main.*


class VendorReviewsFragment : Fragment() {
    private lateinit var binding: FragmentVendorReviewsBinding
    private lateinit var vendorReviewsViewModel: VendorReviewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_lay!!.visibility = View.GONE
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_vendor_reviews,
            container, false
        )
        binding.lifecycleOwner = this
        vendorReviewsViewModel = ViewModelProvider(this).get(VendorReviewsViewModel::class.java)
        vendorReviewsViewModel.products.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                vendorReviewsViewModel.getAllReviewsOfVendor()
            }
        })

        val adapter = VendorCommentAdapter()
        binding.reviews.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding.reviews.layoutManager = layoutManager
        vendorReviewsViewModel.comments.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                println(it)
                adapter.submitList(it)
            }
        })
        return binding.root
    }

}