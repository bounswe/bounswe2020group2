package com.example.getflix.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.getflix.R
import com.example.getflix.databinding.FragmentHomePageBinding
import com.example.getflix.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.activity_main.*


class HomePageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomePageBinding>(inflater,R.layout.fragment_home_page,
            container,false)

        activity?.bottom_nav!!.visibility = View.VISIBLE

        return binding.root
    }


}