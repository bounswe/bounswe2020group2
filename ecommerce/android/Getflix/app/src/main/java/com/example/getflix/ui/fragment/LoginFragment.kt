package com.example.getflix.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentLoginBinding
import com.example.getflix.ui.fragment.LoginFragmentDirections.Companion.actionLoginFragmentToHomePageFragment
import com.example.getflix.ui.fragment.LoginFragmentDirections.Companion.actionLoginFragmentToRegisterFragment

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
        println(activity?.toolbar_lay!!.visibility.toString())

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
