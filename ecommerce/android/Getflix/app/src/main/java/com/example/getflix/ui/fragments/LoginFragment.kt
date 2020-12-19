package com.example.getflix.ui.fragments

import android.content.Intent
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
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentLoginBinding
import com.example.getflix.infoAlert
import com.example.getflix.ui.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToHomePageFragment
import com.example.getflix.ui.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToRegisterFragment
import com.example.getflix.ui.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToVendorHomeFragment
import com.example.getflix.ui.viewmodels.LoginViewModel

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class LoginFragment : Fragment() {

    // private var account : GoogleSignInAccount? = null

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
                inflater, R.layout.fragment_login,
                container, false
        )

        if (resources.configuration.locale.language == "tr") {
            binding.lang.text = "EN"
        } else {
            binding.lang.text = "TR"
        }



        activity?.toolbar_lay!!.visibility = View.GONE
        activity?.bottom_nav!!.visibility = View.GONE



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
            if (binding.username.text.toString().isEmpty()) {
                binding.username.error = getString(R.string.reg_error)
                loginViewModel.setOnLogin(false)
            }
            if (binding.password.text.toString().isEmpty()) {
                binding.password.error = getString(R.string.reg_error)
                loginViewModel.setOnLogin(false)
            }
            if (binding.password.text.toString().isNotEmpty() && binding.username.text.toString().isNotEmpty()) {
                loginViewModel.setUser(this, binding.username.text.toString(), binding.password.text.toString())
            }
        }

        loginViewModel.user.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                MainActivity.StaticData.user = it
                println(it.toString())
                if(it.id==20) {
                    (activity as MainActivity).decideBottomNav(true)
                    view?.findNavController()?.navigate(actionLoginFragmentToVendorHomeFragment())
                } else {
                    (activity as MainActivity).decideBottomNav(false)
                    view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
                }
            }
        })

        binding.signUpButton.setOnClickListener {
            view?.findNavController()?.navigate(actionLoginFragmentToRegisterFragment())
        }
/*
       val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
       val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())


        if(account !=null){
            view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
        }*/
        binding.signInButton.setOnClickListener {
            view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
        }

        binding.guestButton.setOnClickListener {
            MainActivity.StaticData.isVisitor = true
            view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
        }

        return binding.root
    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === 11111111.toInt()) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account  = completedTask.getResult(ApiException::class.java)

        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }*/

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


