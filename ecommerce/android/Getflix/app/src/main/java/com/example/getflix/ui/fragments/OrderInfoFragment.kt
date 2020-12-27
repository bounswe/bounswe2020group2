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
import com.example.getflix.ui.fragments.OrderInfoFragmentDirections.Companion.actionOrderInfoFragmentToProfileFragment
import com.example.getflix.ui.viewmodels.CategoriesViewModel
import com.example.getflix.ui.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class OrderInfoFragment : Fragment() {
    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.orders)
        val binding = DataBindingUtil.inflate<FragmentOrderInfoBinding>(
            inflater, R.layout.fragment_order_info,
            container, false
        )

        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        viewModel.getCustomerOrders()

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

        return binding.root
    }


}