package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCompleteOrderBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.AddressModel
import com.example.getflix.models.CardModel
import com.example.getflix.ui.fragments.CompleteOrderFragmentDirections.Companion.actionCompleteOrderFragmentToAddAddressFragment
import com.example.getflix.ui.fragments.CompleteOrderFragmentDirections.Companion.actionCompleteOrderFragmentToAddCreditCardFragment
import com.example.getflix.ui.viewmodels.CompleteOrderViewModel
import com.example.getflix.ui.fragments.CompleteOrderFragmentDirections.Companion.actionCompleteOrderFragmentToHomePageFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_profile.*

class CompleteOrderFragment : Fragment() {

    private lateinit var binding: FragmentCompleteOrderBinding
    private lateinit var viewModel: CompleteOrderViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.order_complete)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_order,
                container, false)

        viewModel = ViewModelProvider(this).get(CompleteOrderViewModel::class.java)

        binding.btnAddCredit.setOnClickListener {
            view?.findNavController()?.navigate(actionCompleteOrderFragmentToAddCreditCardFragment())
        }

        binding.btnAddAddress.setOnClickListener {
            view?.findNavController()?.navigate(actionCompleteOrderFragmentToAddAddressFragment())
        }

        var addresses = arrayListOf<AddressModel>()
        var credits = arrayListOf<CardModel>()
        var addressesName = arrayListOf<String>()
        var creditsName = arrayListOf<String>()

        viewModel.getCustomerAddresses()
        viewModel.getCustomerCards()
        viewModel.getCustomerCartPrice()

        viewModel.creditList.observe(viewLifecycleOwner, Observer {
            it?.let {
                for(credit in it) {
                    credits.add(credit)
                    creditsName.add(credit.name)
                    println(credit.name)
                }
                val creditAdapter = ArrayAdapter(this.requireContext(),
                        android.R.layout.simple_spinner_dropdown_item, creditsName)
                creditAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                binding.creditSpinner.adapter = creditAdapter
            }
        })

        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            it?.let {
                for(address in it) {
                    addresses.add(address)
                    addressesName.add(address.title)
                }
                val addressAdapter = ArrayAdapter(this.requireContext(),
                        android.R.layout.simple_spinner_dropdown_item, addressesName!!)
                addressAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                binding.addressSpinner.adapter = addressAdapter
            }
        })


        var addressId=-1
        var cardId = -1
        binding.addressSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {

                addressId = addresses[position].id
                println(addressId)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.creditSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {

                cardId = credits[position].id
                println(cardId)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.pay.setOnClickListener {
            viewModel.makePurchase(addressId,cardId)
        }

        viewModel.navigateHome.observe(viewLifecycleOwner, {
            if(it) {
                doneAlert(this, "Your checkout is done, thank you for choosing us!", ::navigateHome)
                viewModel.resetNavigate()
            }
        })

    return binding.root
    }

    private fun navigateHome() {
        view?.findNavController()?.navigate(actionCompleteOrderFragmentToHomePageFragment())
    }


}