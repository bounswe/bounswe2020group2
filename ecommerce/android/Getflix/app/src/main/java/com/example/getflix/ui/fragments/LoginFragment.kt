package com.example.getflix.ui.fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.isAdmin
import com.example.getflix.activities.MainActivity.StaticData.isCustomer
import com.example.getflix.activities.MainActivity.StaticData.isVendor
import com.example.getflix.activities.MainActivity.StaticData.isVisitor
import com.example.getflix.databinding.FragmentLoginBinding
import com.example.getflix.ui.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToHomePageFragment
import com.example.getflix.ui.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToRegisterFragment
import com.example.getflix.ui.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToVendorHomeFragment
import com.example.getflix.ui.viewmodels.LoginViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*


class LoginFragment : Fragment() {


    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private var prefs: SharedPreferences? = null




    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login,
                container, false
        )
        MainActivity.StaticData.mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), MainActivity.StaticData.gso)
        MainActivity.StaticData.account = GoogleSignIn.getLastSignedInAccount(requireActivity())

        if (resources.configuration.locale.language == "tr") {
            binding.lang.text = "EN"
        } else {
            binding.lang.text = "TR"
        }



        activity?.toolbar_lay!!.visibility = View.GONE
        activity?.bottom_nav!!.visibility = View.GONE
        prefs = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)!!
        val savedUsername = prefs!!.getString("s_username", null)
        val savedPassword = prefs!!.getString("s_password", null)
        val savedChecked = prefs!!.getBoolean("s_checked", false)
        if(savedUsername!=null) {
            binding.username.setText(savedUsername)
            binding.password.setText(savedPassword)
            binding.btnRemember.isChecked = savedChecked
        }



        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        binding.lang.setOnClickListener {
            if (binding.lang.text.toString() == "TR") {
                setLocale("tr")
                binding.lang.text = "EN"
            } else {
                setLocale("en")
                binding.lang.text = "TR"
            }
        }


        binding.login.setOnClickListener {
            activity?.loading_progress!!.visibility = View.VISIBLE
            if (binding.username.text.toString().isEmpty()) {
                binding.username.error = getString(R.string.reg_error)
                loginViewModel.setOnLogin(false)
                activity?.loading_progress!!.visibility = View.GONE
            }
            if (binding.password.text.toString().isEmpty()) {
                binding.password.error = getString(R.string.reg_error)
                loginViewModel.setOnLogin(false)
                activity?.loading_progress!!.visibility = View.GONE
            }
            if (binding.password.text.toString().isNotEmpty() && binding.username.text.toString().isNotEmpty()) {
                loginViewModel.setUser(
                        this,
                        binding.username.text.toString(),
                        binding.password.text.toString()
                )
            }
        }

        loginViewModel.user.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                MainActivity.StaticData.user = it
                if (btn_remember.isChecked) {
                    prefs!!.edit().putString("s_username", binding.username.text.toString()).apply()
                    prefs!!.edit().putString("s_password", binding.password.text.toString()).apply()
                    prefs!!.edit().putBoolean("s_checked", true).apply()
                } else {
                    prefs!!.edit().clear().apply()
                }

                if (it.role!="CUSTOMER") {
                    println(it.toString())
                    isCustomer = false
                    isVisitor = false
                    isVendor = true
                    isAdmin = false
                    activity?.loading_progress!!.visibility = View.GONE
                    (activity as MainActivity).decideBottomNav(true)
                    view?.findNavController()?.navigate(actionLoginFragmentToVendorHomeFragment())
                } else {
                    isCustomer = true
                    isVisitor = false
                    isVendor = false
                    isAdmin = false
                    println(it.toString())
                    activity?.loading_progress!!.visibility = View.GONE
                    (activity as MainActivity).decideBottomNav(false)
                    view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
                }
            }
        })

        binding.signUpButton.setOnClickListener {
            view?.findNavController()?.navigate(actionLoginFragmentToRegisterFragment())
        }

        binding.signInButton.setOnClickListener {

            val signInIntent = MainActivity.StaticData.mGoogleSignInClient?.signInIntent
            startActivityForResult(signInIntent, 11)

        }

        binding.guestButton.setOnClickListener {
            isVisitor = true
            isCustomer = false
            isVendor = false
            isAdmin = false
            (activity as MainActivity).decideBottomNav(false)
            view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 11) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            if(resultCode != RESULT_CANCELED)
            handleSignInResult(task)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            var account = completedTask.result
            //println(account?.email)
            //println(account?.displayName)
            MainActivity.StaticData.isGoogleUser = true
            MainActivity.StaticData.account = account
            (activity as MainActivity).decideBottomNav(false)
            view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())

        } catch (e: ApiException) {
    
        }
    }

    private fun setLocale(localeName: String) {
        var currentLanguage = ""
        var currentLang = ""
        if (localeName != currentLanguage) {
            var locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.setLocale(locale)
            res.updateConfiguration(conf, dm)
            val refresh = Intent(
                    context,
                    MainActivity::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {

        }
    }


}


