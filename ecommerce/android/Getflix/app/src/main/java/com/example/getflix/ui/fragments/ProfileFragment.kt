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
import com.example.getflix.databinding.FragmentProfileBinding
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToAdddressFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToBankAccountFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToLoginFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToOrderInfoFragment
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToUserInfoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater,R.layout.fragment_profile,
            container,false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.profile)

         if(MainActivity.StaticData.isVisitor) {
            binding.name.text = getString(R.string.guest)
            binding.btnLogout.text = getString(R.string.login)
            binding.userinfo.text = "---"
            binding.address.text = "---"
            binding.bankAccounts.text = "---"
            binding.orders.text = "---"
         } else {
             binding.name.text = MainActivity.StaticData.name
         }


        binding.btnLogout.setOnClickListener {
                 MainActivity.StaticData.isVisitor = false
                 MainActivity.StaticData.isCustomer = false
                 MainActivity.StaticData.isAdmin = false
                 MainActivity.StaticData.isVendor = false
            view?.findNavController()?.navigate(actionProfileFragmentToLoginFragment())
                /* val transaction = activity?.supportFragmentManager!!.beginTransaction()
                 transaction.replace(R.id.my_nav_host_fragment, LoginFragment())
                 transaction.disallowAddToBackStack()
                 transaction.commit() */

            binding.ordersButton.setOnClickListener {
                view?.findNavController()?.navigate(actionProfileFragmentToOrderInfoFragment())}
            binding.userInfoButton.setOnClickListener {
                view?.findNavController()?.navigate(actionProfileFragmentToUserInfoFragment())}
            binding.addressinfoButton.setOnClickListener {
                view?.findNavController()?.navigate(actionProfileFragmentToAdddressFragment())}
            binding.bankAccountInfoButton.setOnClickListener {
                view?.findNavController()?.navigate(actionProfileFragmentToBankAccountFragment())}

         }
        return binding.root
    }
}