package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ListProductItemBinding
import com.example.getflix.models.ListProductModel
import com.example.getflix.models.ProductModel
import com.squareup.picasso.Picasso

class ListProductsAdapter(
    private val listProductList: ArrayList<ProductModel>
) : ListAdapter<ProductModel, ListProductsAdapter.RowHolder>(ListProductsDiffCallback()) {

    class RowHolder(val binding: ListProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listProduct: ProductModel, position: Int) {
            binding.listproduct = listProduct
            println(listProduct.toString())
            println("bindd")
            binding.cartProductName.text = listProduct.id.toString()
            binding.cartProductPrice.text = listProduct.price.toString()+" TL"

            if(!listProduct.images.isNullOrEmpty())
                Picasso.get().load(listProduct.images[0]).into(binding.cartProductImage)
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

    override fun getItemCount(): Int {
        super.getItemCount()
        return listProductList!!.size
    }


    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        listProductList?.get(position)?.let { holder.bind(it, position) }
    }

}

class ListProductsDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }


}