package com.example.getflix.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentLoginBinding
import com.example.getflix.ui.fragment.LoginFragmentDirections.Companion.actionLoginFragmentToHomePageFragment


class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,R.layout.fragment_login,
        container,false)

        binding.loginButton.setOnClickListener {
            view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
        }

        return binding.root
    }


}

