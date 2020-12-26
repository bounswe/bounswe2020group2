package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.databinding.FragmentUpdateCreditCardBinding
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

        return binding.root
    }


}