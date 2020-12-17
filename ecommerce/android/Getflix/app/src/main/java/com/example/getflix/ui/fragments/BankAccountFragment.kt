package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentBankAccountBinding
import com.example.getflix.databinding.FragmentFavoritesBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class BankAccountFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.bankAccounts)
        val binding = DataBindingUtil.inflate<FragmentBankAccountBinding>(
                inflater, R.layout.fragment_bank_account,
                container, false
        )

        return binding.root

    }



}