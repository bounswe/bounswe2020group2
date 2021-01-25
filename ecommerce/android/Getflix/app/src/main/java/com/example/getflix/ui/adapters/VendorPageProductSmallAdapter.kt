package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.CardVendorProductBinding
import com.example.getflix.databinding.CardVendorProductSmallBinding
import com.example.getflix.models.ProductModel
import com.squareup.picasso.Picasso

class VendorPageProductSmallAdapter :
    ListAdapter<ProductModel, VendorPageProductSmallAdapter.ViewHolder>(VendorPageProductSmallDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        println("Buraya giriyor")
        holder.bind(item)
    }

    private fun ViewHolder.bind(product: ProductModel) {
        if(product.images[0].contains("/image/"))
            Picasso.get().load("http://3.134.80.26:8000" + product.images[0]).into(binding.vendorProductSmallImage)
        else
        Picasso.get().load(product.images[0]).into(binding.vendorProductSmallImage)
        binding.vendorProductSmallName.text = product.brand.name

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CardVendorProductSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    CardVendorProductSmallBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class VendorPageProductSmallDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}

