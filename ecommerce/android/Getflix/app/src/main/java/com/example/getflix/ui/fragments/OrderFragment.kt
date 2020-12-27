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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.getflix.R
import com.example.getflix.databinding.FragmentOrderBinding
import com.example.getflix.ui.adapters.CreditCardAdapter
import com.example.getflix.ui.adapters.OrderAddressAdapter
import com.example.getflix.ui.fragments.OrderFragmentDirections.Companion.actionOrderFragmentToAddAddressFragment
import com.example.getflix.ui.fragments.OrderFragmentDirections.Companion.actionOrderFragmentToAddCreditCardFragment
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

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_order,
            container, false
        )

        viewModel = ViewModelProvider(this).get(CompleteOrderViewModel::class.java)
        viewModel.getCustomerCards()
        viewModel.getCustomerAddresses()

        binding.btnAddCredit.setOnClickListener {
            view?.findNavController()?.navigate(actionOrderFragmentToAddCreditCardFragment())
        }

        binding.btnAddAddress.setOnClickListener {
            view?.findNavController()?.navigate(actionOrderFragmentToAddAddressFragment())
        }

        var addresses = arrayListOf<String>()
        var credits = arrayListOf<String>()


        val orderAddressAddressAdapter = OrderAddressAdapter()
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.orderAddressRecyclerView.adapter = orderAddressAddressAdapter
        binding.orderAddressRecyclerView.layoutManager = layoutManager

        val creditCardAdapter = CreditCardAdapter()
        val layoutManagerForCreditCards = GridLayoutManager(requireContext(), 2)
        binding.orderCardRecyclerView.adapter = creditCardAdapter
        binding.orderCardRecyclerView.layoutManager = layoutManagerForCreditCards

        viewModel.creditList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                creditCardAdapter.submitList(it)
                activity?.loading_progress!!.visibility = View.GONE
            }
        })

        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                orderAddressAddressAdapter.submitList(it)
            }
        })

        CompleteOrderViewModel.currentAddress.observe(viewLifecycleOwner, Observer {
            binding.currentOrderAddress.text = it?.title
        })
        CompleteOrderViewModel.currentCreditCard.observe(viewLifecycleOwner, Observer {
            binding.currentCreditCart.text = it?.name
        })

        return binding.root
    }

}