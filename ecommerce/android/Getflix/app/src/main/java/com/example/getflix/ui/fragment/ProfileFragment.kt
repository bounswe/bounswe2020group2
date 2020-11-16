package com.example.getflix.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.getflix.R
import com.example.getflix.databinding.FragmentHomePageBinding
import com.example.getflix.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater,R.layout.fragment_profile,
            container,false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.profile)
        return binding.root
    }


}