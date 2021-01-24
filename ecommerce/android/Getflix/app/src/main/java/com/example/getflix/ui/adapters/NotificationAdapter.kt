package com.example.getflix.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.NotificationItemBinding
import com.example.getflix.models.NotificationModel
import com.example.getflix.ui.fragments.ListsFragmentDirections
import com.example.getflix.ui.fragments.NotificationFragmentDirections
import com.example.getflix.ui.viewmodels.NotificationViewModel
import com.squareup.picasso.Picasso

class NotificationAdapter(
    private val notificationlist: List<NotificationModel>, fragment: Fragment
) : ListAdapter<NotificationModel, NotificationAdapter.RowHolder>(NotificationsDiffCallback()) {

    var fragment = fragment
    var viewModel = ViewModelProvider(fragment).get(NotificationViewModel::class.java)

    class RowHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationModel, position: Int) {
            var type = notification.type
            if(position==1)
                notification.isSeen = false
            if(!notification.isSeen) {
                binding.cardView.setBackgroundColor(Color.GRAY)
            } else {
                binding.fab.visibility = View.GONE
            }
            var argument = notification.argument
            if(type=="order_status_change") {
                Picasso.get().load(argument.product.imageURL).into(binding.productImage)
                if(argument.newStatus=="at_cargo") {
                    binding.notText.text = "Your order is now on the way."
                } else if(argument.newStatus=="delivered") {
                    binding.notText.text = "Your order is delivered. Enjoy!"
                } else if(argument.newStatus=="accepted") {
                    binding.notText.text = "Your order is accepted."
                }
            } else if(type=="price_change") {
                Picasso.get().load(argument.image_url).into(binding.productImage)
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
        notificationlist?.get(position)?.let {
            holder.bind(it, position)
            holder?.binding.fab.setOnClickListener {
                viewModel.readNotification(notificationlist?.get(position)?.id)
                holder?.binding.fab.visibility = View.GONE
                holder?.binding.cardView.setBackgroundColor(Color.WHITE)
            }
            holder?.itemView!!.setOnClickListener {
                if(notificationlist?.get(position)?.type == "price_change") {
                    fragment.findNavController().navigate(
                        NotificationFragmentDirections.actionNotificationFragmentToProductFragment(
                            notificationlist?.get(position)?.argument.id
                        )
                    )
                } else {
                    fragment.findNavController().navigate(
                        NotificationFragmentDirections.actionNotificationFragmentToOrderInfoFragment(
                        )
                    )
                }
            }
        }
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