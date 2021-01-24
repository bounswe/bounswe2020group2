package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.getflix.R
import com.example.getflix.databinding.FragmentVendorBinding
import com.example.getflix.ui.adapters.VendorPageProductAdapter
import com.example.getflix.ui.viewmodels.VendorPageViewModel
import kotlinx.android.synthetic.main.activity_main.*

class VendorPageFragment : Fragment() {
    private lateinit var binding: FragmentVendorBinding
    private lateinit var vendorPageViewModel: VendorPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_vendor,
            container, false
        )

        val args = VendorPageFragmentArgs.fromBundle(requireArguments())
        var vendorId = args.vendorId

        vendorPageViewModel = ViewModelProvider(this).get(VendorPageViewModel::class.java)
        activity?.toolbar_lay!!.visibility = View.GONE
        binding.lifecycleOwner = this
        
        val adapter = VendorPageProductAdapter()
        binding.vendorProducts.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(),2)
        binding.vendorProducts.layoutManager = layoutManager

       // setVendorRating(rating)

        return binding.root

    }

    private fun navigateBack() {
        view?.findNavController()!!.popBackStack()
    }

    fun setVendorRating(rating: Double) {
        if (rating >= 1) {
            binding.vendorStar1.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 2) {
            binding.vendorStar2.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 3) {
            binding.vendorStar3.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 4) {
            binding.vendorStar4.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating.toInt() == 5) {
            binding.vendorStar5.setImageResource(R.drawable.ic_filled_star)
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.toolbar_lay!!.visibility = View.VISIBLE
    }

}
