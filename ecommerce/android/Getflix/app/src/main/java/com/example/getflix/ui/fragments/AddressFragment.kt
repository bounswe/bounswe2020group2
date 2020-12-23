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
        binding.viewmodel = viewModel
        val recView = binding?.addressList as RecyclerView

        viewModel.getCustomerAddresses()


        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            it?.let {
                val addressListAdapter = AddressAdapter(ArrayList(it!!))
                recView.adapter = addressListAdapter
                recView.setHasFixedSize(true)
               // addressListAdapter.submitList(it)
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