package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.VendororderLayoutBinding
import com.example.getflix.models.OrderModel

class VendorOrdersAdapter(
    private val orderList: MutableList<OrderModel>?,
) : ListAdapter<OrderModel, VendorOrdersAdapter.RowHolder>(VendorOrdersDiffCallback()) {


    class RowHolder(val binding: VendororderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderModel, position: Int) {
            binding.order = order
           // binding.orderAmount.text = order.amount.toString()
           // binding.orderTotalPrice.text = order.total_price.toString()

        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VendororderLayoutBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }

    override fun getItemCount(): Int {
        if(orderList==null)
            return 0
        return orderList!!.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        orderList?.get(position)?.let { holder.bind(it, position) }
    }


}

class VendorOrdersDiffCallback : DiffUtil.ItemCallback<OrderModel>() {
    override fun areItemsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
        return oldItem == newItem
    }

}