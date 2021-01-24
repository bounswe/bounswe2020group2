package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.ItemMyOrderBinding
import com.example.getflix.models.OrderModel
import com.example.getflix.models.OrderPurchasedModel
import com.example.getflix.ui.fragments.OrderInfoFragment
import com.example.getflix.ui.fragments.OrderInfoFragmentDirections


class OrdersAdapter(
    private val orderList: List<OrderModel>, fragment: OrderInfoFragment
) : ListAdapter<OrderModel, OrdersAdapter.RowHolder>(OrderDiffCallback()) {


    val fragment = fragment

    class RowHolder(val binding: ItemMyOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderModel, position: Int) {
            binding.name.text = "Order #" + order.id
            binding.date.text = "Date: "+order.order_all_purchase[0].purchase_date.substring(0, 10) + " " + order.order_all_purchase[0].purchase_date.substring(11, 19)
           // binding.name.text = order.order_all_purchase[0].product.name
            binding.address.text = order.order_all_purchase[0].address.title
           // binding.status.text = order.order_all_purchase[0].status
           // binding.date.text = "Date: "+order.order_all_purchase[0].purchase_date.take(10)

           // if(order.order_all_purchase[0].status != "delivered")
             //   binding.reviewButton.visibility = View.GONE
            
        }


        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMyOrderBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }


    override fun getItemCount(): Int {

        return orderList!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        orderList?.get(position)?.let {
            holder.bind(it, position)
                holder?.itemView!!.setOnClickListener {
                    fragment.findNavController()!!.navigate(OrderInfoFragmentDirections.actionOrderInfoFragmentToOrderProductsFragment(orderList?.get(position).order_all_purchase.toTypedArray()))

                }
            }
        }
    }






class OrderDiffCallback : DiffUtil.ItemCallback<OrderModel>() {

    override fun areItemsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
        return oldItem == newItem
    }

}