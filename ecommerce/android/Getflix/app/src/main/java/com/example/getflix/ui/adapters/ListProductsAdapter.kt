package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ListProductItemBinding
import com.example.getflix.models.ListModel
import com.example.getflix.models.ListProductModel
import com.example.getflix.models.ProductModel
import com.squareup.picasso.Picasso

class ListProductsAdapter(
    private val listProductList: ArrayList<ListProductModel>
) : ListAdapter<ListProductModel, ListProductsAdapter.RowHolder>(ListProductsDiffCallback()) {

    // mutable live data for deleted item position
    val pos = MutableLiveData<Int>()

    init {
        pos.value = -1
    }

    class RowHolder(val binding: ListProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listProduct: ListProductModel, position: Int) {
            binding.listproduct = listProduct
            println(listProduct.toString())
            println("bindd")
            binding.cartProductName.text = listProduct.id.toString()
            binding.cartProductPrice.text = listProduct.product.price.toString()+" TL"

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

    fun deleteItem(position: Int) {
        pos.value = position
    }



    fun resetPos() {
        pos.value = -1
    }


    override fun getItemCount(): Int {
        if (listProductList != null) {
            return listProductList.count()
        }
        return 0;
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