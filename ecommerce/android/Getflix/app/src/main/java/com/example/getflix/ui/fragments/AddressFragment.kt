package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.getflix.R
import com.example.getflix.databinding.FragmentAddressBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class AddressFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.addressInfo)
        val binding = DataBindingUtil.inflate<FragmentAddressBinding>(
                inflater, R.layout.fragment_address,
                container, false
        )

        return binding.root
    }


}