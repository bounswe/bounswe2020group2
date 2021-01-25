package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.OrderProductItemBinding
import com.example.getflix.models.OrderPurchasedModel
import com.example.getflix.models.VendorOrderModel

import com.squareup.picasso.Picasso

class OrderProductsAdapter(
    private val orderPurchasedList: ArrayList<OrderPurchasedModel>?
) : ListAdapter<OrderPurchasedModel, OrderProductsAdapter.RowHolder>(OrderProductsDiffCallback()) {

    val currentOrder = MutableLiveData<OrderPurchasedModel?>()

    init {
        currentOrder.value = null
    }
    private fun RowHolder.bind(orderPurchased: OrderPurchasedModel) {
        binding.listproduct = orderPurchased
        binding.cartProductName.text = orderPurchased.product.name
        binding.cartProductPrice.text = orderPurchased.product.price.toString()+" TL    (x" + orderPurchased.amount + ")"
        var status = orderPurchased.status
        if(status=="at_cargo")
            status = "At cargo"
        else if(status=="accepted")
            status = "Accepted"
        if(status=="delivered")
            status = "Delivered"
        binding.cartProductStatus.text = status
        binding.totalPriceCartProduct.text = "Total: "+(orderPurchased.unit_price*orderPurchased.amount).toString()+" TL"
        //binding.amount.setText("Amount: "+orderPurchased.amount)

        if(!orderPurchased.product.images.isNullOrEmpty())
            Picasso.get().load(orderPurchased.product.images[0]).into(binding.cartProductImage)

        if(orderPurchased.status == "delivered"){
            binding.btnReview.visibility = View.VISIBLE
            binding.btnReview.setOnClickListener {
                currentOrder.value = orderPurchased
            }
        }
    }
    class RowHolder(val binding: OrderProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
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
        orderPurchasedList?.get(position)?.let {
            holder.bind(it)
        }
    }

}

class OrderProductsDiffCallback : DiffUtil.ItemCallback<OrderPurchasedModel>() {
    override fun areItemsTheSame(oldItem: OrderPurchasedModel, newItem: OrderPurchasedModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderPurchasedModel, newItem: OrderPurchasedModel): Boolean {
        return oldItem == newItem
    }


}