package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.UserType
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentProfileBinding
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToAdddressFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToBankAccountFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToLoginFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToOrderInfoFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToUserInfoFragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ProfileFragment : Fragment() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile,
                container, false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.profile)

        if (MainActivity.StaticData.userType.value == UserType.GUEST) {
            binding.name.text = getString(R.string.guest)
            binding.btnLogout.text = getString(R.string.login)
            binding.userinfo.text = "---"
            binding.address.text = "---"
            binding.bankAccounts.text = "---"
            binding.orders.text = "---"
        } else {
            val user = MainActivity.StaticData.regularLoginedUser.value
            binding.name.text = user!!.firstName + " " + user!!.lastName
            // binding.email.text = MainActivity.StaticData.user!!.email
        }

        binding.ordersButton.setOnClickListener {
            print("hey")
            view?.findNavController()?.navigate(actionProfileFragmentToOrderInfoFragment())
        }
        binding.userInfoButton.setOnClickListener {
            print("hey")
            view?.findNavController()?.navigate(actionProfileFragmentToUserInfoFragment())
        }
        binding.addressinfoButton.setOnClickListener {
            print("hey")
            view?.findNavController()?.navigate(actionProfileFragmentToAdddressFragment())
        }
        binding.bankAccountInfoButton.setOnClickListener {
            print("hey")
            view?.findNavController()?.navigate(actionProfileFragmentToBankAccountFragment())
        }

        binding.btnLogout.setOnClickListener {
            if (MainActivity.StaticData.googleSignedInUser.value != null) {
                mGoogleSignInClient.signOut()
                MainActivity.StaticData.googleSignedInUser.value = null

            } else {
                MainActivity.StaticData.regularLoginedUser.value = null
            }
            MainActivity.StaticData.userType.value = null

            view?.findNavController()?.navigate(actionProfileFragmentToLoginFragment())
            /* val transaction = activity?.supportFragmentManager!!.beginTransaction()
             transaction.replace(R.id.my_nav_host_fragment, LoginFragment())
             transaction.disallowAddToBackStack()
             transaction.commit() */

        }
        return binding.root
    }
}
