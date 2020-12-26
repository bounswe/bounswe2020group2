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
import com.example.getflix.databinding.FragmentUpdateAddressBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.PhoneModel
import com.example.getflix.service.requests.AddressUpdateRequest
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

        binding.editableAddress.setText(address.address)
        binding.editableTitle.setText(address.title)
        binding.editableName.setText(address.name)
        binding.editableSurname.setText(address.surname)
        binding.editableZipcode.setText(address.zipCode)
        binding.editableCity.setText(address.city)
        binding.editableCountry.setText(address.country)
        binding.editablePhone.setText(address.phone.number)
        binding.editableCountrycode.setText(address.phone.countryCode)
        binding.editableProvince.setText(address.province)


        binding.btnAdd.setOnClickListener {
            val updateRequest = AddressUpdateRequest(binding.editableTitle.text.toString(),
                PhoneModel(binding.editableCountrycode.text.toString(),binding.editablePhone.text.toString()),
                binding.editableName.text.toString(),binding.editableSurname.text.toString(),
                binding.editableAddress.text.toString(),binding.editableProvince.text.toString(),
                binding.editableCity.text.toString(),binding.editableCountry.text.toString(),
                binding.editableZipcode.text.toString())
            viewModel.updateCustomerAddress(address.id,updateRequest)
        }

        viewModel.navigateBack.observe(viewLifecycleOwner, {
            if(it) {
                doneAlert(this, "Credit card is updated successfully", ::navigateBack)
                viewModel.resetNavigate()
            }
        })

        return binding.root
    }

    private fun navigateBack() {
        view?.findNavController()?.popBackStack()
    }


}