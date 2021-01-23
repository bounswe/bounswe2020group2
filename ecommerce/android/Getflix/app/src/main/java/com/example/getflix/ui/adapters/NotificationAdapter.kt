package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.NotificationItemBinding
import com.example.getflix.models.NotificationModel

class NotificationAdapter(
    private val notificationlist: List<NotificationModel>,
) : ListAdapter<NotificationModel, NotificationAdapter.RowHolder>(NotificationsDiffCallback()) {

    class RowHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationModel, position: Int) {
            var type = notification.type
            var argument = notification.argument
            if(type=="order_status_change") {
                if(argument.newStatus=="at_cargo") {
                    binding.notText.text = "Your order is now on the way."
                } else if(argument.newStatus=="delivered") {
                    binding.notText.text = "Your order is delivered. Enjoy!"
                } else if(argument.newStatus=="accepted") {
                    binding.notText.text = "Your order is accepted."
                }
            } else if(type=="price_change") {
                if(argument.new_price>argument.old_price) {
                    binding.notText.text = "Price of a product in your wishlist has changed."
                } else if(argument.new_price<argument.old_price) {
                    binding.notText.text = "A product in your wishlist is now cheaper. Don’t miss out!"
                }
            }
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