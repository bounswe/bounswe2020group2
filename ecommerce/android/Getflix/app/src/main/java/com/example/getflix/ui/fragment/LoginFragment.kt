package com.example.getflix.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentLoginBinding
import com.example.getflix.ui.fragment.LoginFragmentDirections.Companion.actionLoginFragmentToHomePageFragment
import com.example.getflix.ui.fragment.LoginFragmentDirections.Companion.actionLoginFragmentToRegisterFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*


class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login,
            container, false
        )

        println(activity?.toolbar_lay!!.visibility.toString())
        activity?.toolbar_lay!!.visibility = View.GONE
        println(activity?.toolbar_lay!!.visibility.toString())

        binding.btnLogin.setOnClickListener() {
            view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
        }

        binding.btnRegister.setOnClickListener {
            view?.findNavController()?.navigate(actionLoginFragmentToRegisterFragment())
        }


        return binding.root
    }


}
