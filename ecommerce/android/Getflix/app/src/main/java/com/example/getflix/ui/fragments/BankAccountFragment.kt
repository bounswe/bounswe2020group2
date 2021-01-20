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
import com.example.getflix.ui.fragments.BankAccountFragmentDirections.Companion.actionBankAccountFragmentToAddCreditCardFragment
import com.example.getflix.ui.viewmodels.CreditCardViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class BankAccountFragment : Fragment() {

    private lateinit var viewModel: CreditCardViewModel
    private lateinit var binding: FragmentBankAccountBinding


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



        binding.addFab.setOnClickListener {
            if(binding.addFab.visibility == View.VISIBLE)
            view?.findNavController()?.navigate(actionBankAccountFragmentToAddCreditCardFragment())
        }

        binding.btnAddCard.setOnClickListener {
            if(binding.btnAddCard.visibility == View.VISIBLE)
                view?.findNavController()?.navigate(actionBankAccountFragmentToAddCreditCardFragment())
        }

        val credits = arrayListOf<CardModel>()

        viewModel = ViewModelProvider(this).get(CreditCardViewModel::class.java)
        viewModel.getCustomerCards()
        val recView = binding?.creditList as RecyclerView
        /* val creditCartsAdapter = CreditCartsAdapter(credits)
         recView.adapter = creditCartsAdapter
         recView.setHasFixedSize(true) */



        viewModel.creditList.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                if(it.size!=0) {
                    binding.btnAddCard.visibility = View.GONE
                    binding.creditImage.visibility = View.GONE
                    binding.creditText.visibility = View.GONE
                    binding.creditList.visibility = View.VISIBLE
                    binding.addFab.visibility = View.VISIBLE
                    val creditCartsAdapter = CreditCartsAdapter(ArrayList(list!!), this)
                    recView.adapter = creditCartsAdapter
                    recView.setHasFixedSize(true)
                    // creditCartsAdapter.submitList(it)
                    val itemTouchHelper =
                        ItemTouchHelper(SwipeToDeleteCreditCart(creditCartsAdapter!!))
                    itemTouchHelper.attachToRecyclerView(recView)
                    creditCartsAdapter.pos.observe(viewLifecycleOwner, Observer {
                        if (it != -1) {
                            val id = creditCartsAdapter.deleteItem(it).id
                            viewModel.deleteCustomerCard(id)
                            // viewModel.getCustomerCards()
                            creditCartsAdapter.resetPos()
                        }
                    })
                } else {
                    binding.btnAddCard.visibility = View.VISIBLE
                    binding.creditImage.visibility = View.VISIBLE
                    binding.creditText.visibility = View.VISIBLE
                    binding.creditList.visibility = View.GONE
                    binding.addFab.visibility = View.GONE
                }
            }
        })


        return binding.root
    }

}
