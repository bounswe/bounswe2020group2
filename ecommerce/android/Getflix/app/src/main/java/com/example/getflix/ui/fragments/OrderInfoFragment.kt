package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.getflix.R
import com.example.getflix.databinding.FragmentBankAccountBinding
import com.example.getflix.databinding.FragmentOrderInfoBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class OrderInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.orders)
        val binding = DataBindingUtil.inflate<FragmentOrderInfoBinding>(
                inflater, R.layout.fragment_order_info,
                container, false
        )

        return binding.root
    }


}