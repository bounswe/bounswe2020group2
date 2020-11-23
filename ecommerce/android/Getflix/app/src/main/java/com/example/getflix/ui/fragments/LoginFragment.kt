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
import com.example.getflix.models.PModel
import com.example.getflix.models.ProductModel
import com.example.getflix.services.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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



        activity?.toolbar_lay!!.visibility = View.GONE
        activity?.bottom_nav!!.visibility = View.GONE






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
            if(canSubmit) {
                val userData = LogReq(binding.username.text.toString(),
                    binding.password.text.toString())
                var service = APIService()
                service.tryLogin(userData) {
                    userData
                }
            }
            //view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())
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
           // view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.my_nav_host_fragment, HomePageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }

        binding.guestButton.setOnClickListener {
            MainActivity.StaticData.isVisitor = true
            //view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.my_nav_host_fragment, HomePageFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
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


