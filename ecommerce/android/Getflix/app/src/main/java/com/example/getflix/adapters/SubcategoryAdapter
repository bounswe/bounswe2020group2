package com.example.getflix.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ProductCardBinding
import com.example.getflix.model.ProductModel

class SubcategoryAdapter: ListAdapter<ProductModel, SubcategoryAdapter.ViewHolder>(ProductDiffCallback()){


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(product: ProductModel) {
        binding.product = product
        binding.brand.text = product.brandId.toString()
        binding.price.text = product.price.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductCardBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<ProductModel>(){
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem  === newItem
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}

