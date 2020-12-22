package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentRegisterBinding
import com.example.getflix.doneAlert
import com.example.getflix.infoAlert
import com.example.getflix.ui.viewmodels.RegisterViewModel


class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register,
                container, false)
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


        var checkFields = false
        binding.btnRegister.setOnClickListener {
            checkFields = registerViewModel.setSignUpCredentials(
                            binding.username.text.toString(),
                            binding.mail.text.toString(),
                            binding.password.text.toString(),
                            binding.name.text.toString(),
                            binding.surname.text.toString(),
                            binding.phone.text.toString(),
                            binding.conPassword.text.toString()
                    )

            if (binding.conPassword.text.toString().isEmpty()) {
                binding.conPassword.error = getString(R.string.reg_error)
            }

            var prevAlert = false
            if (binding.password.text.toString()!=binding.conPassword.text.toString()) {
                prevAlert = true
                infoAlert(this,"password eşleşmiyor")
            }

            if (binding.password.text.toString().length<8) {
                if(!prevAlert) {
                    infoAlert(this, getString(R.string.pass_char_limit))
                    prevAlert = true
                }
            }

            if (binding.username.text.toString().length<6) {
                if(!prevAlert) {
                    infoAlert(this, getString(R.string.username_char_limit))
                    prevAlert = true
                }
            }

            if (binding.name.text.toString().length<2 && binding.surname.text.toString().length<2) {
                if(!prevAlert)
                    infoAlert(this,getString(R.string.valid_name))
            }

            if (!binding.check.isChecked) {
                checkFields = false
                binding.check.error = getString(R.string.reg_agree_err)
            }
            if (!customer) {
                if (!binding.maddress.text.toString().isEmpty()) {
                    binding.maddress.error = getString(R.string.reg_error)
                }
                if (!binding.zipCode.text.toString().isEmpty()) {
                    binding.maddress.error = getString(R.string.reg_error)
                }
                if (!binding.city.text.toString().isEmpty()) {
                    binding.city.error = getString(R.string.reg_error)
                }
                if (!binding.state.text.toString().isEmpty()) {
                    binding.state.error = getString(R.string.reg_error)
                }
            }

        }
        registerViewModel.canSignUp.observe(viewLifecycleOwner, Observer {
            if (it != null && it.successful && checkFields) {
                doneAlert(this,getString(R.string.register_success),::navigateLogin)
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
