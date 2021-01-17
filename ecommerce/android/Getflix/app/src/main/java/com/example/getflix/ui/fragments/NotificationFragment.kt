package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentListsBinding
import com.example.getflix.databinding.FragmentNotificationBinding
import com.example.getflix.models.*
import com.example.getflix.ui.adapters.ListsAdapter
import com.example.getflix.ui.adapters.NotificationAdapter
import com.example.getflix.ui.viewmodels.ListViewModel
import com.example.getflix.ui.viewmodels.NotificationViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class NotificationFragment : Fragment() {

    private lateinit var viewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNotificationBinding>(
            inflater, R.layout.fragment_notification,
            container, false
        )

        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        binding.viewmodel = ListViewModel()
        activity?.toolbar!!.toolbar_title.text = getString(R.string.notifications)
        val recView = binding?.notificationList as RecyclerView

        var not1 =
            NotificationModel(10, "2 items in your shopping cart are now on sale! Don't miss out - order now!")
        var not2 =
            NotificationModel(10, "Sale today on all winter items! Come into the store for a special %30 discount.")
        val nots = arrayListOf<NotificationModel>(not1, not2)
        val notAdapter = NotificationAdapter(nots)
        recView.adapter = notAdapter
        recView.setHasFixedSize(true)

        for (not in nots) {
            viewModel.addList(not)
        }

        viewModel.notificationList.observe(viewLifecycleOwner, Observer {
            it?.let {
                notAdapter.submitList(it)
            }
        })


        return binding.root
    }

}