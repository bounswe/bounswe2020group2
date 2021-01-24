package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.NotificationItemBinding
import com.example.getflix.models.NotificationModel
import com.example.getflix.ui.fragments.ListsFragmentDirections
import com.example.getflix.ui.fragments.NotificationFragmentDirections
import com.squareup.picasso.Picasso

class NotificationAdapter(
    private val notificationlist: List<NotificationModel>, fragment: Fragment
) : ListAdapter<NotificationModel, NotificationAdapter.RowHolder>(NotificationsDiffCallback()) {

    var fragment = fragment

    class RowHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationModel, position: Int) {
            var type = notification.type
            var argument = notification.argument
            if(type=="order_status_change") {
                if(argument.newStatus=="at_cargo") {
                    binding.notText.text = "Your order is now on the way."
                    binding.productImage.setImageResource(R.drawable.ic_atcargo)
                } else if(argument.newStatus=="delivered") {
                    binding.notText.text = "Your order is delivered. Enjoy!"
                    binding.productImage.setImageResource(R.drawable.ic_box)
                } else if(argument.newStatus=="accepted") {
                    binding.notText.text = "Your order is accepted."
                    binding.productImage.setImageResource(R.drawable.ic_accepted)
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
            holder?.itemView!!.setOnClickListener {
                if(notificationlist?.get(position)?.type == "price_change") {
                    fragment.findNavController().navigate(
                        NotificationFragmentDirections.actionNotificationFragmentToProductFragment(
                            notificationlist?.get(position)?.argument.id
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