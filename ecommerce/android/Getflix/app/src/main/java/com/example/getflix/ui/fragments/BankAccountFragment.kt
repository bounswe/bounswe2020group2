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
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentBankAccountBinding
import com.example.getflix.models.CardModel
import com.example.getflix.ui.adapters.CreditCartsAdapter
import com.example.getflix.ui.fragments.BankAccountFragmentDirections.Companion.actionBankAccountFragmentToPaymentFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToBankAccountFragment
import com.example.getflix.ui.viewmodels.CreditCartViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class BankAccountFragment : Fragment() {

    private lateinit var viewModel: CreditCartViewModel
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


        binding.fab.setOnClickListener {
            view?.findNavController()?.navigate(actionBankAccountFragmentToPaymentFragment())
        }

        viewModel = ViewModelProvider(this).get(CreditCartViewModel::class.java)
        binding.viewmodel = CreditCartViewModel()
        val recView = binding?.creditList as RecyclerView

        var credit1 =
                CardModel(1, "Garanti Kartim", "Selin Kocak", 4444, "07/23", 112)
        var credit2 =
                CardModel(2, "QNB Kartim", "Selin Kocak", 4444, "08/23", 112)
        val credits = arrayListOf(credit1, credit2)

        val creditCartsAdapter = CreditCartsAdapter(credits)
        recView.adapter = creditCartsAdapter
        recView.setHasFixedSize(true)

        for (credit in credits) {
            viewModel.addCreditCart(credit)
        }

        viewModel.creditList.observe(viewLifecycleOwner, Observer {
            it?.let {
                creditCartsAdapter.submitList(it)
            }
        })

        return binding.root
    }

}
