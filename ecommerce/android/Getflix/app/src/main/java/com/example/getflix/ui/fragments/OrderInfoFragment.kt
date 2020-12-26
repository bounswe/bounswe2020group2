package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentBankAccountBinding
import com.example.getflix.databinding.FragmentOrderInfoBinding
import com.example.getflix.models.CardModel
import com.example.getflix.models.OrderModel
import com.example.getflix.ui.adapters.OrdersAdapter
import com.example.getflix.ui.fragments.OrderInfoFragmentDirections.Companion.actionOrderInfoFragmentToProfileFragment
import com.example.getflix.ui.viewmodels.CreditCardViewModel
import com.example.getflix.ui.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class OrderInfoFragment : Fragment() {

    private lateinit var viewModel: OrderViewModel
    private lateinit var binding: FragmentOrderInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.orders)
        val binding = DataBindingUtil.inflate<FragmentOrderInfoBinding>(
            inflater, R.layout.fragment_order_info,
            container, false
        )

        activity?.onBackPressedDispatcher!!.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        isEnabled = false
                        view?.findNavController()!!
                            .navigate(actionOrderInfoFragmentToProfileFragment())
                    }
                }
            }
        )
        val orders = arrayListOf<OrderModel>()
        //val orderAdapter = OrdersAdapter(orders)
        //binding.ordersList.adapter = orderAdapter
        binding.ordersList.setHasFixedSize(true)

        return binding.root
    }
}