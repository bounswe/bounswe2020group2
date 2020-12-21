package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.CardTrendingProductBinding
import com.example.getflix.models.ProductModel
import com.squareup.picasso.Picasso

class TrendingProductAdapter :
    ListAdapter<ProductModel, TrendingProductAdapter.ViewHolder>(TrendingProductDiffCallBack()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(product: ProductModel) {
        binding.trendingProductName.text = product.name
        binding.trendingProductPrice.text = product.price.toString() + "$"
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(binding.trendingProductImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CardTrendingProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardTrendingProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class TrendingProductDiffCallBack : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}

