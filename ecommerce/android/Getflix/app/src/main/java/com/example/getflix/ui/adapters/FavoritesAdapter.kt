package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.FavproductLayoutBinding
import com.example.getflix.models.ProductModel

class FavoritesAdapter(
        private val productList: ArrayList<ProductModel>?,
) : ListAdapter<ProductModel, FavoritesAdapter.RowHolder>(FavoritesDiffCallback()) {

    class RowHolder(val binding: FavproductLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {
            binding.product = product

        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavproductLayoutBinding.inflate(layoutInflater,parent,false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }

    /*override fun getItemCount(): Int {
        if (productList != null) {
            return productList.count()
        }
        return 0;
    }*/


    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        productList?.get(position)?.let { holder.bind(it, position) }
    }


}

class FavoritesDiffCallback: DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}