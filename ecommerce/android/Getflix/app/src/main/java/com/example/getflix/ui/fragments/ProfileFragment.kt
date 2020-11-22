package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentProfileBinding
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
            binding.points.text = "---"
            binding.products.text = "---"
            binding.mail.text = "---"
            binding.tel.text = "---"
         }


        binding.btnLogout.setOnClickListener {
                 val transaction = activity?.supportFragmentManager!!.beginTransaction()
                 transaction.replace(R.id.my_nav_host_fragment, LoginFragment())
                 transaction.disallowAddToBackStack()
                 transaction.commit()
         }


        return binding.root
    }


}