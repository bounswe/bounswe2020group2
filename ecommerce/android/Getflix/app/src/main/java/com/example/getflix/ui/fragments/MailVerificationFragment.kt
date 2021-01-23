package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentMailVerificationBinding
import com.example.getflix.ui.fragments.MailVerificationFragmentDirections.Companion.actionMailVerificationFragmentToLoginFragment
import com.example.getflix.ui.viewmodels.MailVerificationViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MailVerificationFragment : Fragment() {
    private lateinit var mailVerificationViewModel: MailVerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMailVerificationBinding>(
            inflater, R.layout.fragment_mail_verification,
            container, false
        )
        activity?.bottom_nav!!.visibility = View.VISIBLE
        activity?.toolbar_lay!!.visibility = View.VISIBLE
        activity?.toolbar!!.toolbar_title.text = ""

        mailVerificationViewModel =
            ViewModelProvider(this).get(MailVerificationViewModel::class.java)


        binding.resetMailButton.setOnClickListener {
            mailVerificationViewModel.sendMailVerification()

        }

        mailVerificationViewModel.onMailVerified.observe(viewLifecycleOwner, Observer {
            if (it) {
                println("burası ne alakaaaa")
                view?.findNavController()!!
                    .navigate(actionMailVerificationFragmentToLoginFragment())
            }
        })
        return binding.root
    }


}