package com.example.getflix.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.getflix.R
import com.example.getflix.UserType
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentLoginBinding
import com.example.getflix.models.GoogleProfile
import com.example.getflix.ui.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToHomePageFragment
import com.example.getflix.ui.viewmodels.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: FragmentLoginBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {

        binding = DataBindingUtil.inflate<FragmentLoginBinding>(
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
            if (binding.password.text.toString().isNotEmpty() && binding.username.text.toString()
                            .isNotEmpty()
            ) {
                loginViewModel.setUser(
                        binding.username.text.toString(),
                        binding.password.text.toString()
                )
            }
        }

        loginViewModel.user.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                MainActivity.StaticData.user = it
                view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
            } else {

            }
        })


        val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.guestButton.setOnClickListener {
            MainActivity.StaticData.userType.value = UserType.GUEST
            view?.findNavController()?.navigate(actionLoginFragmentToHomePageFragment())
        }
        binding.signInButton.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (account != null && MainActivity.StaticData.googleSignedInUser.value == null) {
            val googleProfile = GoogleProfile(
                    account.getDisplayName(),
                    account.getGivenName(),
                    account.getFamilyName(),
                    account.getEmail(),
                    account.getId(),
                    account.getPhotoUrl()
            )
            MainActivity.StaticData.googleSignedInUser.value = googleProfile
            findNavController().navigate(actionLoginFragmentToHomePageFragment())
        }

    }

    private fun signIn() {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val googleProfile = GoogleProfile(
                            account.getDisplayName(),
                            account.getGivenName(),
                            account.getFamilyName(),
                            account.getEmail(),
                            account.getId(),
                            account.getPhotoUrl()
                    )
                    MainActivity.StaticData.googleSignedInUser.value = googleProfile
                    print("hello2")
                    findNavController().navigate(actionLoginFragmentToHomePageFragment())
                }

            } catch (e: ApiException) {
                Log.w("Error", "signInResult:failed code=" + e.statusCode)
            }
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


