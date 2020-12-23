package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.AddressCardItemBinding
import com.example.getflix.databinding.FragmentAddressBinding
import com.example.getflix.databinding.FragmentProfileBinding
import com.example.getflix.models.AddressModel
import com.example.getflix.models.PhoneModel
import com.example.getflix.ui.adapters.AddressAdapter
import com.example.getflix.ui.fragments.AddressFragmentDirections.Companion.actionAddressFragmentToAddAddressFragment
import com.example.getflix.ui.viewmodels.AddressViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import com.example.getflix.ui.fragments.AddressFragmentDirections.Companion.actionAddressFragmentToProfileFragment

class AddressFragment : Fragment() {

    private lateinit var viewModel: AddressViewModel
    private lateinit var binding: FragmentAddressBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.addressInfo)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address,
            container, false)
        binding.fab.setOnClickListener {
            view?.findNavController()?.navigate(actionAddressFragmentToAddAddressFragment())
        }

        viewModel = ViewModelProvider(this).get(AddressViewModel::class.java)
        binding.viewmodel = AddressViewModel()
        val recView = binding?.addressList as RecyclerView

        var address1 =
            AddressModel(1, "Ev", PhoneModel("90","88887"), "Selin", "Zara", "Lawrence Moreno\n" +
                    "935-9940 Tortor. Street\n" +
                    "Santa Rosa MN 98804", "Santa Rosa", "citty", "country","3434")
        var address2 =
            AddressModel(2, "Is", PhoneModel("90","88887"), "Selin", "Zara", "Lawrence Moreno\n" +
                    "935-9940 Tortor. Street\n" +
                    "Santa Rosa MN 98804", "Santa Rosa", "citty", "country","242424")
        var address3 =
            AddressModel(3, "Yazlik", PhoneModel("90","88887"), "Selin", "Zara", "Lawrence Moreno\n" +
                    "935-9940 Tortor. Street\n" +
                    "Santa Rosa MN 98804", "Santa Rosa", "citty", "country","234432")
        val addresses = arrayListOf(address1, address2, address3)

        val addressListAdapter = AddressAdapter(addresses)
        recView.adapter = addressListAdapter
        recView.setHasFixedSize(true)

        for (address in addresses) {
            viewModel.addAddress(address)
        }

        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            it?.let {
                addressListAdapter.submitList(it)
            }
        })


        activity?.onBackPressedDispatcher!!.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        isEnabled = false
                        view?.findNavController()!!
                            .navigate(actionAddressFragmentToProfileFragment())
                    }
                }
            }
        )

        return binding.root
    }


}