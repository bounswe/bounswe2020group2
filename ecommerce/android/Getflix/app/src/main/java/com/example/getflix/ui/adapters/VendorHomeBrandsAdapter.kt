package com.example.getflix.ui.adapters

import com.example.getflix.databinding.VendorBrandBinding
import com.example.getflix.models.BrandModel



import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.CardProductBinding
import com.example.getflix.databinding.VendorProductBinding
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.viewmodels.HomeViewModel

class VendorHomeBrandsAdapter(
    private val brandList: MutableList<BrandModel>,
) : ListAdapter<BrandModel, VendorHomeBrandsAdapter.RowHolder>(VendorHomeBrandsDiffCallback()) {


    class RowHolder(val binding: VendorBrandBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(brand: BrandModel, position: Int) {
            binding.brand = brand
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VendorBrandBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return brandList.count()

    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        brandList?.get(position)?.let { holder.bind(it, position) }
    }


}

class VendorHomeBrandsDiffCallback : DiffUtil.ItemCallback<BrandModel>() {
    override fun areItemsTheSame(oldItem: BrandModel, newItem: BrandModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BrandModel, newItem: BrandModel): Boolean {
        return oldItem == newItem
    }

}