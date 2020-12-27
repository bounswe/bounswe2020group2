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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.getflix.R

import com.example.getflix.databinding.FragmentCompleteOrderBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.AddressModel
import com.example.getflix.ui.adapters.CreditCardAdapter
import com.example.getflix.ui.adapters.OrderAddressAdapter
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
        viewModel.getCustomerCards()
        viewModel.getCustomerAddresses()

        binding.btnAddCredit.setOnClickListener {
            view?.findNavController()?.navigate(actionCompleteOrderFragmentToAddCreditCardFragment())
        }

        binding.btnAddAddress.setOnClickListener {
            view?.findNavController()?.navigate(actionCompleteOrderFragmentToAddAddressFragment())
        }


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

        var addressId =-1
        var cardId = -1
        CompleteOrderViewModel.currentAddress.observe(viewLifecycleOwner, Observer {
            binding.currentOrderAddress.text = it?.title
            addressId = it?.id!!
        })
        CompleteOrderViewModel.currentCreditCard.observe(viewLifecycleOwner, Observer {
            binding.currentCreditCart.text = it?.name
            cardId = it?.id!!
        })

       

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