package com.example.getflix.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.addToShoppingCart
import com.example.getflix.databinding.CardRecommendedProductBinding
import com.example.getflix.databinding.FragmentProductBinding
import com.example.getflix.models.ProductModel

class RecommenderAdapter :
    ListAdapter<ProductModel, RecommenderAdapter.ViewHolder>(RecommenderDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(product: ProductModel) {
        var amount = binding.amountRecProduct.text.toString().toInt()
        binding.product = product
        binding.oldProductPrice.text = product.price.toString() + " TL"
        binding.oldProductPrice.setPaintFlags(binding.oldProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        binding.productPrice.text = product.priceDiscounted.toString() + " TL"
        binding.addShopping.setOnClickListener {
            addToShoppingCart(amount, 1, product.id)
        }
        binding.decreaseRecProduct.setOnClickListener {
            if (amount > 1) {
                amount = amount.dec()
            }
        }
        binding.increaseRecProduct.setOnClickListener {
            amount = amount.inc()
        }
        binding.amountRecProduct.text = amount.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CardRecommendedProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardRecommendedProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class RecommenderDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}

