package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentLoginBinding

import kotlinx.android.synthetic.main.activity_main.*


class LoginFragment : Fragment() {

    // private var account : GoogleSignInAccount? = null

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
        activity?.bottom_nav!!.visibility = View.GONE
        println(activity?.toolbar_lay!!.visibility.toString())

        binding.login.setOnClickListener {
            var canSubmit = true
            if (binding.username.text.toString().isEmpty()) {
                binding.username.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if (binding.password.text.toString().isEmpty()) {
                binding.password.error = getString(R.string.reg_error)
                canSubmit = false
            }
            if(canSubmit)
            view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())
        }

        binding.signUpButton.setOnClickListener {
            view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
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
            view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())

        }

        binding.guestButton.setOnClickListener {
            MainActivity.StaticData.isVisitor = true
            view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())

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

}
