package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentUpdateCreditCardBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.ExpirationDateModel
import com.example.getflix.service.requests.CardUpdateRequest
import com.example.getflix.ui.viewmodels.CreditCardViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class UpdateCreditCardFragment : Fragment() {

    private lateinit var viewModel: CreditCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentUpdateCreditCardBinding>(inflater, R.layout.fragment_update_credit_card,
            container, false)
        viewModel = ViewModelProvider(this).get(CreditCardViewModel::class.java)
        activity?.toolbar!!.toolbar_title.text = "Update Credit Card"

        val args = UpdateCreditCardFragmentArgs.fromBundle(requireArguments())
        val creditCard = args.creditCard

        binding.editableName.setText(creditCard.name)
        binding.editableExpirationmonth.setText(creditCard.expiration_data.month.toString())
        binding.editableExpirationyear.setText(creditCard.expiration_data.year.toString())
        binding.editableCvv.setText(creditCard.cvv.toString())
        binding.editableSerialnumber.setText(creditCard.serial_number)
        binding.editableOwnername.setText(creditCard.owner_name)

        binding.btnAdd.setOnClickListener {
            val updateRequest = CardUpdateRequest(binding.editableName.text.toString(),
                binding.editableOwnername.text.toString(),binding.editableSerialnumber.text.toString(),
                ExpirationDateModel(binding.editableExpirationmonth.text.toString().toInt(),binding.editableExpirationyear.text.toString().toInt()),
                binding.editableCvv.text.toString().toInt())
            viewModel.updateCustomerCard(creditCard.id,updateRequest)
        }

        viewModel.navigateOrder.observe(viewLifecycleOwner, Observer{
            if(it) {
                doneAlert(this, "Credit card is updated successfully", ::navigateBack)
                viewModel.resetNavigate()
            }
        })

        return binding.root
    }

    private fun navigateBack() {
        view?.findNavController()?.popBackStack()
    }


}