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
import com.example.getflix.ui.fragments.RegisterFragmentDirections.Companion.actionRegisterFragmentToHomePageFragment
import com.example.getflix.ui.viewmodels.RegisterViewModel


class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater, R.layout.fragment_register,
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


        binding.btnRegister.setOnClickListener {
            if (registerViewModel.setSignUpCredentials(
                            binding.username.text.toString(),
                            binding.mail.text.toString(),
                            binding.password.text.toString(),
                            binding.name.text.toString(),
                            binding.surname.text.toString(),
                            binding.phone.text.toString()
                    )) {
                binding.name.error = getString(R.string.reg_error)
            }

            if (binding.conPassword.text.toString().isEmpty()) {
                binding.conPassword.error = getString(R.string.reg_error)
            }

            if (!binding.check.isChecked) {
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
            if (it != null) {
                view?.findNavController()?.navigate(actionRegisterFragmentToHomePageFragment())
            }
        })

        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }




        return binding.root
    }


}
