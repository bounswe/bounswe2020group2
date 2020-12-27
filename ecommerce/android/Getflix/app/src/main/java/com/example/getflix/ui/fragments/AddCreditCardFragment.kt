package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentAddCreditCardBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.ExpirationDateModel
import com.example.getflix.service.requests.CardAddRequest
import com.example.getflix.ui.viewmodels.CreditCardViewModel
import com.manojbhadane.PaymentCardView.OnPaymentCardEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class AddCreditCardFragment : Fragment() {

    private lateinit var viewModel: CreditCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddCreditCardBinding>(
            inflater, R.layout.fragment_add_credit_card,
            container, false
        )
        activity?.toolbar!!.toolbar_title.text = getString(R.string.payment)
        viewModel = ViewModelProvider(this).get(CreditCardViewModel::class.java)


        binding.creditCard.setOnPaymentCardEventListener(object : OnPaymentCardEventListener {
            override fun onCardDetailsSubmit(
                month: String,
                year: String,
                cardNumber: String,
                cvv: String
            ) {
                println(binding.name.text.toString())
                cardNumber.replace(" ","")
                var cardRequest = CardAddRequest(binding.name.text.toString(),binding.ownerName.text.toString(),
                cardNumber, ExpirationDateModel(month.toInt(),year.toInt()),cvv.toInt())
                viewModel.addCustomerCard(cardRequest)
                viewModel.getCustomerCards()

            }

            override fun onError(error: String) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }

            override fun onCancelClick() {}
        })

        viewModel.navigateOrder.observe(viewLifecycleOwner, {
            if(it) {
                doneAlert(this, "Credit card is added successfully", ::navigateOrder)
                viewModel.resetNavigate()
            }
        })

        return binding.root
    }

    private fun navigateOrder() {
        view?.findNavController()?.popBackStack()
    }




}