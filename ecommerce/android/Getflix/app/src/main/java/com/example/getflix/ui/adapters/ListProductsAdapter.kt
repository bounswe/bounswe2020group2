package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ListProductItemBinding
import com.example.getflix.models.ListProductModel
import com.squareup.picasso.Picasso

class ListProductsAdapter(
    private val listProductList: ArrayList<ListProductModel>?
) : ListAdapter<ListProductModel, ListProductsAdapter.RowHolder>(ListProductsDiffCallback()) {

    class RowHolder(val binding: ListProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listProduct: ListProductModel, position: Int) {
            binding.listproduct = listProduct
            binding.cartProductName.setText(listProduct.product.name)
            binding.cartProductPrice.setText(listProduct.product.price.toString()+" TL")

            if(!listProduct.product.images.isNullOrEmpty())
                Picasso.get().load(listProduct.product.images[0]).into(binding.cartProductImage)
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListProductItemBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        listProductList?.get(position)?.let { holder.bind(it, position) }
    }

}

class ListProductsDiffCallback : DiffUtil.ItemCallback<ListProductModel>() {
    override fun areItemsTheSame(oldItem: ListProductModel, newItem: ListProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListProductModel, newItem: ListProductModel): Boolean {
        return oldItem == newItem
    }


}