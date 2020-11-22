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
import com.example.getflix.databinding.FragmentHomePageBinding
import com.example.getflix.databinding.FragmentProfileBinding
import com.example.getflix.ui.fragments.ProfileFragmentDirections.Companion.actionProfileFragmentToLoginFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater,R.layout.fragment_profile,
            container,false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.profile)

         if(MainActivity.StaticData.isVisitor) {
            view?.findNavController()?.navigate(actionProfileFragmentToLoginFragment())
          }

        return binding.root
    }


}