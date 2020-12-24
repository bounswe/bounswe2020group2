package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.databinding.FragmentMessagesBinding
import com.example.getflix.ui.viewmodels.MessagesViewModel
import com.example.getflix.ui.viewmodels.SubCategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MessagesFragment : Fragment() {

    private lateinit var viewModel: MessagesViewModel
    private lateinit var binding: FragmentMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_messages,
            container, false
        )

        activity?.toolbar!!.toolbar_title.text = getString(R.string.messages)
        viewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)

        return binding.root
    }


}