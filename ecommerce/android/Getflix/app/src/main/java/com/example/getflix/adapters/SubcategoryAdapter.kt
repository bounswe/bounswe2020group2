package com.example.getflix.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.FragmentSubcategoryBinding
import com.example.getflix.models.SubcategoryModel

class SubcategoryAdapter(private var context: Context) :
        ListAdapter<SubcategoryModel, SubcategoryAdapter.ViewHolder>(SubcategoryDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(subcategoryModel: SubcategoryModel) {
        val productAdapter = ProductAdapter()
        binding.productList.adapter = productAdapter
        binding.productList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        productAdapter.submitList(subcategoryModel.products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: FragmentSubcategoryBinding) :
            RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentSubcategoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class SubcategoryDiffCallback : DiffUtil.ItemCallback<SubcategoryModel>() {
    override fun areItemsTheSame(oldItem: SubcategoryModel, newItem: SubcategoryModel): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SubcategoryModel, newItem: SubcategoryModel): Boolean {
        return oldItem == newItem
    }

}
