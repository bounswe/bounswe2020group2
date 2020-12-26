package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.databinding.FragmentUpdateAddressBinding
import com.example.getflix.ui.viewmodels.AddressViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class UpdateAddressFragment : Fragment() {

    private lateinit var viewModel: AddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentUpdateAddressBinding>(inflater, R.layout.fragment_update_address,
            container, false)
        viewModel = ViewModelProvider(this).get(AddressViewModel::class.java)
        activity?.toolbar!!.toolbar_title.text = "Update Address"

        val args = UpdateAddressFragmentArgs.fromBundle(requireArguments())
        val address = args.address

        println(address.toString())

        return binding.root
    }


}