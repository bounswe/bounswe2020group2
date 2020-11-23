package com.example.getflix.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentFavoritesBinding
import com.example.getflix.databinding.FragmentSubcategoryBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class SubcategoryFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSubcategoryBinding>(
                inflater, R.layout.fragment_subcategory,
                container, false
        )

        activity?.toolbar!!.toolbar_title.text = "sub"
        val navController =
            Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        activity?.toolbar!!.setupWithNavController(navController, appBarConfiguration)

        return binding.root
    }


}