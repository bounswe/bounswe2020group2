package com.example.getflix.ui.adapters

import android.view.LayoutInflater
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

    // mutable live data for deleted item position
    val pos = MutableLiveData<Int>()
    val fragment = fragment

    init {
        pos.value = -1
    }


    class RowHolder(val binding: ItemMyOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderModel, position: Int) {
            binding.name.setText(order.order_all_purchase[0].product.name)
            binding.address.setText(order.order_all_purchase[0].address.title)
            binding.status.setText(order.order_all_purchase[0].status)
            /*
            binding.name.text = order.name
            binding.ownerName.text ="Owner: " + credit.owner_name
            binding.serialNum.text = credit.serial_number

             */
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
                holder?.itemView!!.setOnClickListener {
                    fragment.findNavController()!!.navigate(OrderInfoFragmentDirections.actionOrderInfoFragmentToOrderProductsFragment(orderList?.get(position).order_all_purchase.toTypedArray()))

                }
            }
        }
    }

    fun deleteItem(position: Int): OrderModel {
        pos.value = position
        return orderList?.get(position)!!
    }

    fun resetPos() {
        pos.value = -1
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