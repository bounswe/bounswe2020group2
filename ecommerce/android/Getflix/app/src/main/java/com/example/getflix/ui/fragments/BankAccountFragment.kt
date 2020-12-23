package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.example.getflix.ui.fragments.BankAccountFragmentDirections.Companion.actionBankAccountFragmentToProfileFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentBankAccountBinding
import com.example.getflix.models.CardModel
import com.example.getflix.ui.adapters.CreditCartsAdapter
import com.example.getflix.ui.adapters.SwipeToDeleteCreditCart
import com.example.getflix.ui.fragments.BankAccountFragmentDirections.Companion.actionBankAccountFragmentToPaymentFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToBankAccountFragment
import com.example.getflix.ui.viewmodels.CreditCartViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class BankAccountFragment : Fragment() {

    private lateinit var viewModel: CreditCartViewModel
    private lateinit var binding: FragmentBankAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreditCartViewModel::class.java)
//        binding.viewmodel = CreditCartViewModel()
        viewModel.getCustomerCards()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.bankAccounts)




        activity?.onBackPressedDispatcher!!.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (isEnabled) {
                            isEnabled = false
                            view?.findNavController()!!
                                    .navigate(actionBankAccountFragmentToProfileFragment())
                        }
                    }
                }
        )


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_account,
                container, false)


        binding.fab.setOnClickListener {
            view?.findNavController()?.navigate(actionBankAccountFragmentToPaymentFragment())
        }

        val credits = arrayListOf<CardModel>()

        viewModel = ViewModelProvider(this).get(CreditCartViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.getCustomerCards()
        val recView = binding?.creditList as RecyclerView
        /* val creditCartsAdapter = CreditCartsAdapter(credits)
         recView.adapter = creditCartsAdapter
         recView.setHasFixedSize(true) */



        viewModel.creditList.observe(viewLifecycleOwner, Observer {
            it?.let {
                val creditCartsAdapter = CreditCartsAdapter(ArrayList(it!!))
                recView.adapter = creditCartsAdapter
                recView.setHasFixedSize(true)
                // creditCartsAdapter.submitList(it)
                val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCreditCart(creditCartsAdapter!!))
                itemTouchHelper.attachToRecyclerView(recView)
                creditCartsAdapter.pos.observe(viewLifecycleOwner, Observer {
                    if (it != -1) {
                        val id = creditCartsAdapter.deleteItem(it).id
                        viewModel.deleteCustomerCard(id)
                        creditCartsAdapter.resetPos()
                    }
                })
            }
        })


        return binding.root
    }

}
