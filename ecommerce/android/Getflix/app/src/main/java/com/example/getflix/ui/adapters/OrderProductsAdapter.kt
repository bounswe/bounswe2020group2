package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.OrderProductItemBinding
import com.example.getflix.models.OrderPurchasedModel
import com.example.getflix.ui.viewmodels.OrderProductViewModel

class OrderProductsAdapter(
    private val orderPurchasedList: ArrayList<OrderPurchasedModel>?
) : ListAdapter<OrderPurchasedModel, OrderProductsAdapter.RowHolder>(ListProductsDiffCallback()) {

    class RowHolder(val binding: OrderProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(orderPurchased: OrderProductViewModel, position: Int) {
            binding.viewmodel = orderPurchased
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OrderProductItemBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        orderPurchasedList?.get(position)?.let { holder.bind(it, position) }
    }

}

class ListProductsDiffCallback : DiffUtil.ItemCallback<OrderPurchasedModel>() {
    override fun areItemsTheSame(oldItem: OrderPurchasedModel, newItem: OrderPurchasedModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderPurchasedModel, newItem: OrderPurchasedModel): Boolean {
        return oldItem == newItem
    }


}