package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.CardTodaysDealBinding
import com.example.getflix.models.ProductModel
import com.squareup.picasso.Picasso

val imageSources = listOf<Int>(R.drawable.new_year2,R.drawable.new_year1,R.drawable.new_year3,R.drawable.new_year0)
class TodaysDealsAdapter :
    ListAdapter<ProductModel, TodaysDealsAdapter.ViewHolder>(TodaysDealsDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,position)
    }

    private fun ViewHolder.bind(product: ProductModel,position: Int) {
        Picasso.get().load(product.images[0]).into(binding.todaysDealImage)
        binding.imageView3.setImageResource(imageSources[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CardTodaysDealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardTodaysDealBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class TodaysDealsDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}

