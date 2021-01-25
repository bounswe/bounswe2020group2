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
import com.example.getflix.databinding.FragmentNotificationBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.*
import com.example.getflix.ui.adapters.NotificationAdapter
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
        activity?.toolbar!!.toolbar_title.text = getString(R.string.notifications)
        val recView = binding?.notificationList as RecyclerView
        activity?.toolbar!!.btn_notification_check.visibility = View.VISIBLE
        viewModel.getNotifications()
        viewModel.readAllNotifications()
        viewModel.readNotification(1)


        activity?.toolbar!!.btn_notification_check.setOnClickListener {
            viewModel.readAllNotifications()
            doneAlert(this,"You've marked all your notifications as seen",null)
        }

        viewModel.notificationList.observe(viewLifecycleOwner, Observer {
            it?.let {
                val notAdapter = NotificationAdapter(it,this)
                recView.adapter = notAdapter
                recView.setHasFixedSize(true)
                notAdapter.submitList(it)
            }
        })


        return binding.root
    }

    override fun onStop() {
        super.onStop()
        activity?.toolbar!!.btn_notification_check.visibility = View.GONE
    }

}