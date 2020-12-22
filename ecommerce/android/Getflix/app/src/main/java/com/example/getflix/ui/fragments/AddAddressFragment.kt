package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getflix.R
import androidx.databinding.DataBindingUtil
import com.example.getflix.databinding.FragmentAddAddressBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class AddAddressFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.address_add)

        val binding = DataBindingUtil.inflate<FragmentAddAddressBinding>(inflater, R.layout.fragment_add_address,
            container, false)

        return binding.root


    }


}