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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.AddressCardItemBinding
import com.example.getflix.databinding.FragmentAddressBinding
import com.example.getflix.ui.adapters.*
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
            if(binding.fab.visibility == View.VISIBLE)
            view?.findNavController()?.navigate(actionAddressFragmentToAddAddressFragment())
        }

        binding.btnAddAddress.setOnClickListener {
            if(binding.btnAddAddress.visibility == View.VISIBLE)
                view?.findNavController()?.navigate(actionAddressFragmentToAddAddressFragment())
        }

        viewModel = ViewModelProvider(this).get(AddressViewModel::class.java)
        binding.viewmodel = viewModel
        val recView = binding?.addressList as RecyclerView

        viewModel.getCustomerAddresses()


        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size != 0) {
                    binding.btnAddAddress.visibility = View.GONE
                    binding.addressImage.visibility = View.GONE
                    binding.addressText.visibility = View.GONE
                    binding.addressList.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                    val addressListAdapter = AddressAdapter(ArrayList(it!!), this)
                    recView.adapter = addressListAdapter
                    recView.setHasFixedSize(true)
                    // addressListAdapter.submitList(it)
                    val itemTouchHelper =
                        ItemTouchHelper(SwipeToDeleteAddress(addressListAdapter!!))
                    itemTouchHelper.attachToRecyclerView(recView)
                    addressListAdapter.pos.observe(viewLifecycleOwner, Observer {
                        if (it != -1) {
                            val id = addressListAdapter.deleteItem(it).id
                            viewModel.deleteCustomerAddress(id)
                            addressListAdapter.resetPos()
                        }
                    })
                } else {
                    binding.btnAddAddress.visibility = View.VISIBLE
                    binding.addressImage.visibility = View.VISIBLE
                    binding.addressText.visibility = View.VISIBLE
                    binding.addressList.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }
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