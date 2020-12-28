package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.askAlert
import com.example.getflix.databinding.FragmentVendorProfileBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import com.example.getflix.ui.fragments.VendorProfileFragmentDirections.Companion.actionVendorProfileToLoginFragment
import com.example.getflix.ui.viewmodels.VendorProfileViewModel


class VendorProfileFragment : Fragment() {

    private lateinit var binding: FragmentVendorProfileBinding
    private lateinit var viewModel: VendorProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vendor_profile,
                container, false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.profile)
        viewModel = ViewModelProvider(this).get(VendorProfileViewModel::class.java)

        if(MainActivity.StaticData.vendor!=null) {
            binding.name.text = MainActivity.StaticData.vendor
            binding.brandNum.text = MainActivity.StaticData.brandNum.toString()
            binding.proNum.text = MainActivity.StaticData.proNum.toString()
        }

        binding.logout.setOnClickListener {
            askAlert(this, getString(R.string.logout_warning), ::navigateLogin)
        }

        binding.name.text = MainActivity.StaticData.user!!.firstName + " " + MainActivity.StaticData.user!!.lastName

        return binding.root
    }


    private fun navigateLogin() {
        view?.findNavController()?.navigate(actionVendorProfileToLoginFragment())
    }

}