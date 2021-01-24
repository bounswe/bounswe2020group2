package com.example.getflix.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentRegisterBinding
import com.example.getflix.doneAlert
import com.example.getflix.infoAlert
import com.example.getflix.ui.fragments.RegisterFragmentDirections.Companion.actionRegisterFragmentToMailVerificationFragment
import com.example.getflix.ui.viewmodels.RegisterViewModel
import kotlinx.android.synthetic.main.activity_main.*


class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register,
            container, false
        )
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.lifecycleOwner = this
        var customer = true

        binding.radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.vendor -> {
                        binding.addressZip.visibility = View.VISIBLE
                        binding.cityState.visibility = View.VISIBLE
                        customer = false
                    }
                    R.id.customer -> {
                        binding.addressZip.visibility = View.GONE
                        binding.cityState.visibility = View.GONE
                        customer = true

                    }
                }
            })



        binding.btnRegister.setOnClickListener {
            var checkFields = true

            activity?.loading_progress!!.visibility = View.VISIBLE

            if (binding.username.text.toString().isEmpty()) {
                checkFields = false
                binding.username.error = getString(R.string.reg_error)
            }
            if (binding.mail.text.toString().isEmpty()) {
                checkFields = false
                binding.mail.error = getString(R.string.reg_error)
            }
            if (binding.password.text.toString().isEmpty()) {
                checkFields = false
                binding.password.error = getString(R.string.reg_error)
            }
            if (binding.name.text.toString().isEmpty()) {
                checkFields = false
                binding.name.error = getString(R.string.reg_error)
            }
            if (binding.surname.text.toString().isEmpty()) {
                checkFields = false
                binding.surname.error = getString(R.string.reg_error)
            }
            if (binding.phone.text.toString().isEmpty()) {
                checkFields = false
                binding.phone.error = getString(R.string.reg_error)
            }
            if (binding.conPassword.text.toString().isEmpty()) {
                checkFields = false
                binding.conPassword.error = getString(R.string.reg_error)
            }

            var prevAlert = false
            if (binding.password.text.toString() != binding.conPassword.text.toString()) {
                prevAlert = true
                infoAlert(this, getString(R.string.confirm_pass_warning))
            }

            if (binding.password.text.toString().length < 8) {
                if (!prevAlert) {
                    infoAlert(this, getString(R.string.pass_char_limit))
                    prevAlert = true
                }
            }

            if (binding.username.text.toString().length < 6) {
                if (!prevAlert) {
                    infoAlert(this, getString(R.string.username_char_limit))
                    prevAlert = true
                }
            }

            if (binding.name.text.toString().length <= 2 || binding.surname.text.toString().length <= 2) {
                if (!prevAlert) {
                    infoAlert(this, getString(R.string.valid_name))
                    prevAlert = true
                }
            }

            if (!binding.check.isChecked) {
                checkFields = false
                binding.check.error = getString(R.string.reg_agree_err)
            }
            if (!customer) {
                if (!binding.maddress.text.toString().isEmpty()) {
                    checkFields = false
                    binding.maddress.error = getString(R.string.reg_error)
                }
                if (!binding.zipCode.text.toString().isEmpty()) {
                    checkFields = false
                    binding.maddress.error = getString(R.string.reg_error)
                }
                if (!binding.city.text.toString().isEmpty()) {
                    checkFields = false
                    binding.city.error = getString(R.string.reg_error)
                }
                if (!binding.state.text.toString().isEmpty()) {
                    checkFields = false
                    binding.state.error = getString(R.string.reg_error)
                }
            }
            if (checkFields && prevAlert.not()) {
                registerViewModel.setSignUpCredentials(
                    this,
                    binding.username.text.toString(),
                    binding.mail.text.toString(),
                    binding.password.text.toString(),
                    binding.name.text.toString(),
                    binding.surname.text.toString(),
                    binding.phone.text.toString(),
                    binding.conPassword.text.toString()
                )

            } else {
                requireActivity().loading_progress!!.visibility = View.GONE
            }

        }
        registerViewModel.canSignUp.observe(viewLifecycleOwner, Observer {
            activity?.loading_progress!!.visibility = View.GONE

            if (it != null && it.message == "Username is already in use") {
                infoAlert(this, getString(R.string.user_already_exists))
                navigateLogin()
                println("Username is already in use login fragment")
            } else if (it != null && it.successful) {
                // doneAlert()
                println("Register olabildi")
                view?.findNavController()?.navigate(actionRegisterFragmentToMailVerificationFragment(binding.mail.text.toString()))

            } else {
                println("Login olamadı")
            }
        })


        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        return binding.root
    }

    private fun navigateLogin() {
        view?.findNavController()?.popBackStack()
    }


}
