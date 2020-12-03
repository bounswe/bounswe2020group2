package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.getflix.R
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

        activity?.toolbar!!.visibility = View.GONE
        val navController =
            Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = ""
        val args = SubcategoryFragmentArgs.fromBundle(requireArguments())
        val subCat = args.subName
        binding.toolbarTitle.text = subCat


        return binding.root
    }

    override fun onPause() {
        super.onPause()
        activity?.toolbar!!.visibility = View.VISIBLE
    }


}