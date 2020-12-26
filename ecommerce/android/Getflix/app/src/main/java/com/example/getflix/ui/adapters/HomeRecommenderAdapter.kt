package com.example.getflix.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.activities.MainActivity
import com.example.getflix.addToShoppingCart
import com.example.getflix.databinding.CardHomeRecommendedProductBinding
import com.example.getflix.models.ProductModel
import com.squareup.picasso.Picasso

class HomeRecommenderAdapter :
    ListAdapter<ProductModel, HomeRecommenderAdapter.ViewHolder>(HomeRecommenderDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(product: ProductModel) {
        Picasso.get().load(product.images[0]).into(binding.productImage)

        var amount = binding.amountRecProduct.text.toString().toInt()

        binding.product = product
        if (product.price.toString().length > 5) {
            binding.oldProductPrice.text = product.price.toString().substring(0, 5) + " TL"
        } else {
            binding.oldProductPrice.text = product.price.toString() + " TL"

        }
        binding.oldProductPrice.setPaintFlags(binding.oldProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        if (product.priceDiscounted.toString().length > 5) {
            binding.productPrice.text =
                product.priceDiscounted.toString().substring(0, 5) + " TL"

        } else {
            binding.productPrice.text = product.priceDiscounted.toString() + " TL"

        }
        if (MainActivity.StaticData.isCustomer) {
            binding.addShopping.setOnClickListener {
                addToShoppingCart(amount, 1, product.id)
            }
        }
        binding.decreaseRecProduct.setOnClickListener {
            if (amount > 1) {
                amount = amount.dec()
                binding.amountRecProduct.text = amount.toString()
            }
        }
        binding.increaseRecProduct.setOnClickListener {
            amount = amount.inc()
            binding.amountRecProduct.text = amount.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CardHomeRecommendedProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    CardHomeRecommendedProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class HomeRecommenderDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}

