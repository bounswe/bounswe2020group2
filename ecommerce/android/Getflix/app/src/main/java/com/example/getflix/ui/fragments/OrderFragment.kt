package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentOrderBinding
import com.example.getflix.ui.fragments.OrderFragmentDirections.Companion.actionOrderFragmentToAddAddressFragment
import com.example.getflix.ui.fragments.OrderFragmentDirections.Companion.actionOrderFragmentToPaymentFragment
import com.example.getflix.ui.viewmodels.CompleteOrderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private lateinit var viewModel: CompleteOrderViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.order_complete)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order,
                container, false)

        viewModel = ViewModelProvider(this).get(CompleteOrderViewModel::class.java)

        binding.btnAddCredit.setOnClickListener {
            view?.findNavController()?.navigate(actionOrderFragmentToPaymentFragment())
        }

        binding.btnAddAddress.setOnClickListener {
            view?.findNavController()?.navigate(actionOrderFragmentToAddAddressFragment())
        }

        var addresses = arrayListOf<String>()
        var credits = arrayListOf<String>()

        viewModel.getCustomerAddresses()
        viewModel.getCustomerCards()

        viewModel.creditList.observe(viewLifecycleOwner, Observer {
            it?.let {
                for(credit in it) {
                    credits.add(credit.name)
                    println(credit.name)
                }
                val creditAdapter = ArrayAdapter(this.requireContext(),
                        android.R.layout.simple_spinner_item, credits)
                creditAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                binding.creditSpinner.adapter = creditAdapter
            }
        })

        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            it?.let {
                for(adress in it) {
                    addresses.add(adress.title)
                }
                val addressAdapter = ArrayAdapter(this.requireContext(),
                        android.R.layout.simple_spinner_item, addresses!!)
                addressAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                binding.addressSpinner.adapter = addressAdapter
            }
        })


        binding.addressSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                Toast.makeText(context,
                    "selected item: " + addresses[position],
                Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // write code to perform some action
            }
        }
    return binding.root
    }

}