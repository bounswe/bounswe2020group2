package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ProductCardBinding
import com.example.getflix.models.ProductModel

class CartAdapter(
        private val productList: ArrayList<ProductModel>?,
) : ListAdapter<ProductModel, CartAdapter.RowHolder>(CartDiffCallback()) {


    class RowHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {
            binding.product = product
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductCardBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        productList?.get(position)?.let { holder.bind(it, position) }
    }

    /* override fun getItemCount(): Int {
         if (productList != null) {
             return productList.count()
         }
         return 0
     } */


}

class CartDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}