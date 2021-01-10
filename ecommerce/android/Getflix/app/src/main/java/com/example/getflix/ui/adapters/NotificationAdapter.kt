package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.NotificationItemBinding
import com.example.getflix.models.NotificationModel

class NotificationAdapter(
    private val notificationlist: ArrayList<NotificationModel>?,
) : NotificationAdapter<NotificationModel, NotificationAdapter.RowHolder>(NotificationsDiffCallback()) {

    class RowHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationModel, position: Int) {
            binding.notification = notification
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NotificationItemBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        notificationlist?.get(position)?.let { holder.bind(it, position) }
    }


}

class NotificationsDiffCallback : DiffUtil.ItemCallback<NotificationModel>() {
    override fun areItemsTheSame(oldItem: NotificationModel, newItem: NotificationModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotificationModel, newItem: NotificationModel): Boolean {
        return oldItem == newItem
    }

}