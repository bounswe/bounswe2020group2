package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getflix.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.databinding.FragmentAddAddressBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.ExpirationDateModel
import com.example.getflix.models.PhoneModel
import com.example.getflix.service.requests.AddressAddRequest
import com.example.getflix.service.requests.CardAddRequest
import com.example.getflix.ui.viewmodels.AddressViewModel
import com.example.getflix.ui.viewmodels.CreditCardViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class AddAddressFragment : Fragment() {

    private lateinit var viewModel: AddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.address_add)

        val binding = DataBindingUtil.inflate<FragmentAddAddressBinding>(inflater, R.layout.fragment_add_address,
            container, false)
        viewModel = ViewModelProvider(this).get(AddressViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            var addressRequest = AddressAddRequest(binding.name.text.toString(),
                PhoneModel(binding.countryCode.text.toString(),binding.phone.text.toString()),
                binding.firstname.text.toString(), binding.surname.text.toString(),
                binding.addressInfo.text.toString(),binding.province.text.toString(),
                binding.city.text.toString(),binding.country.text.toString(),
                binding.zipCode.text.toString())
            viewModel.addCustomerAddress(addressRequest)
            viewModel.getCustomerAddresses()
        }

        viewModel.navigateBack.observe(viewLifecycleOwner, {
            if(it) {
                doneAlert(this, "Credit card is added successfully", ::navigateBack)
                viewModel.resetNavigate()
            }
        })

        return binding.root


    }

    private fun navigateBack() {
        view?.findNavController()?.popBackStack()
    }

}