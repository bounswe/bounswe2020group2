package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.askAlert
import com.example.getflix.databinding.FragmentProfileBinding
import com.example.getflix.infoAlert
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToAddressFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToBankAccountFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToCustMessagesFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToLoginFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToOrderInfoFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile,
            container, false
        )


        activity?.toolbar!!.toolbar_title.text = getString(R.string.profile)

        if (MainActivity.StaticData.isVisitor) {
            binding.name.text = getString(R.string.guest)
            binding.buttonLogout.text = getString(R.string.login)
        } else if (MainActivity.StaticData.isGoogleUser) {
            binding.name.text = MainActivity.StaticData.account?.displayName
        } else {
            binding.name.text =
                MainActivity.StaticData.user!!.firstName + " " + MainActivity.StaticData.user!!.lastName
            binding.fullName.text =
                MainActivity.StaticData.user!!.firstName + " " + MainActivity.StaticData.user!!.lastName
            binding.mail.text = MainActivity.StaticData.user!!.email
        }

        binding.ordersLayout.setOnClickListener {
            if (MainActivity.StaticData.isVisitor) {
                infoAlert(this, getString(R.string.order_guest_alert))
            } else {
                view?.findNavController()?.navigate(actionProfileFragmentToOrderInfoFragment())
            }
        }

        binding.messagesLayout.setOnClickListener {
            if (MainActivity.StaticData.isVisitor) {
                infoAlert(this, getString(R.string.order_guest_alert))
            } else {
                view?.findNavController()?.navigate(actionProfileFragmentToCustMessagesFragment())
            }
        }

        binding.addressLayout.setOnClickListener {
            if (MainActivity.StaticData.isVisitor) {
                infoAlert(this, getString(R.string.address_guest_alert))
            } else {
                view?.findNavController()?.navigate(actionProfileFragmentToAddressFragment())
            }
        }
        binding.bankAccountsLayout.setOnClickListener {
             if(MainActivity.StaticData.isVisitor) {
                 infoAlert(this, getString(R.string.bank_guest_alert))
             } else {
            view?.findNavController()?.navigate(actionProfileFragmentToBankAccountFragment())
            }
        }

        binding.buttonLogout.setOnClickListener {
            if (!MainActivity.StaticData.isVisitor) {
                askAlert(this, getString(R.string.logout_warning), ::navigateLogin)
                //FirebaseAuth.getInstance().signOut()
                //view?.findNavController()?.navigate(actionProfileFragmentToLoginFragment())
            } else {
                resetData()
                view?.findNavController()?.navigate(actionProfileFragmentToLoginFragment())
            }
        }
        return binding.root
    }


    private fun resetData() {
        MainActivity.StaticData.isVisitor = false
        MainActivity.StaticData.isCustomer = false
        MainActivity.StaticData.isAdmin = false
        MainActivity.StaticData.isVendor = false
        MainActivity.StaticData.user = null
        MainActivity.StaticData.isGoogleUser = false
    }

    private fun navigateLogin() {
        FirebaseAuth.getInstance().signOut()
        view?.findNavController()?.navigate(actionProfileFragmentToLoginFragment())
        if (MainActivity.StaticData.isGoogleUser)
            MainActivity.StaticData.mGoogleSignInClient!!.signOut()
    }

}
