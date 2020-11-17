package com.example.getflix.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCategoriesBinding
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener


class CategoriesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCategoriesBinding>(
            inflater, R.layout.fragment_categories,
            container, false
        )




        return binding.root
    }




}