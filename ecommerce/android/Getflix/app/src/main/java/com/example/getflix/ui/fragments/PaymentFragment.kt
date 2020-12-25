package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.getflix.R
import com.example.getflix.databinding.FragmentPaymentBinding
import com.manojbhadane.PaymentCardView.OnPaymentCardEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class PaymentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPaymentBinding>(
            inflater, R.layout.fragment_payment,
            container, false
        )
        activity?.toolbar!!.toolbar_title.text = getString(R.string.payment)


        binding.creditCard.setOnPaymentCardEventListener(object : OnPaymentCardEventListener {
            override fun onCardDetailsSubmit(
                month: String,
                year: String,
                cardNumber: String,
                cvv: String
            ) {
            }

            override fun onError(error: String) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }

            override fun onCancelClick() {}
        })



        /*binding?.cardForm!!.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .actionLabel("Purchase")
                .setup(activity)
        binding.cardForm.cvvEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        binding.btnBuy.setOnClickListener{
            if (binding.cardForm.isValid) {

            }else {
                Toast.makeText(activity, "Please complete the form", Toast.LENGTH_LONG).show();
            }
        }
        // Inflate the layout for this fragment */
        return binding.root
    }


}