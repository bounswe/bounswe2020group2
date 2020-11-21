package com.example.getflix.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentHomePageBinding
import com.example.getflix.databinding.FragmentRegisterBinding
import com.example.getflix.models.ProductModel


class RegisterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater, R.layout.fragment_register,
            container, false)

        binding.radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when(checkedId) {
                    R.id.vendor -> {
                        binding.addressZip.visibility = View.VISIBLE
                        binding.cityState.visibility = View.VISIBLE
                    }
                    R.id.customer -> {
                        binding.addressZip.visibility = View.GONE
                        binding.cityState.visibility = View.GONE
                    }
                }
            })


        binding.btnRegister.setOnClickListener {
            var canSubmit = true
            if (binding.name.text.toString().isEmpty()) {
                binding.name.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if (binding.surname.text.toString().isEmpty()) {
                binding.surname.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if (binding.username.text.toString().isEmpty()) {
                binding.username.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if (binding.mail.text.toString().isEmpty()) {
                binding.mail.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if (binding.password.text.toString().isEmpty()) {
                binding.password.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if (binding.conPassword.text.toString().isEmpty()) {
                binding.conPassword.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if (binding.phone.text.toString().isEmpty()) {
                binding.phone.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if(!binding.check.isChecked) {
                binding.check.error = "you should check"
                canSubmit = false
            }
            if (canSubmit) {
                var name = binding.name.text.toString()
                var surname = binding.surname.text.toString()
                var username = binding.username.text.toString()
                var mail = binding.mail.text.toString()
                var password = binding.password.text.toString()
                var phone = binding.phone.text.toString()

                //  var user = ProductModel(name,uprice,unit,kdv,amount,code,otv,discinc)
                view?.findNavController()
                    ?.navigate(RegisterFragmentDirections.actionRegisterFragmentToHomePageFragment())
            }
        }




        return binding.root
    }


}