package com.example.getflix.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ListHorizontalsubsBinding
import com.example.getflix.models.SubcategoryModel

class SubcategoryHorizontalAdapter(private var context: Context) :
        ListAdapter<SubcategoryModel, SubcategoryHorizontalAdapter.ViewHolder>(SubcategoryHorizontalDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(subcategoryModel: SubcategoryModel) {
        val productAdapter = ProductAdapter()
        binding.subcategory = subcategoryModel
        binding.productList.adapter = productAdapter
        binding.productList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //productAdapter.submitList(subcategoryModel.products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListHorizontalsubsBinding) :
            RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListHorizontalsubsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class SubcategoryHorizontalDiffCallback : DiffUtil.ItemCallback<SubcategoryModel>() {
    override fun areItemsTheSame(oldItem: SubcategoryModel, newItem: SubcategoryModel): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SubcategoryModel, newItem: SubcategoryModel): Boolean {
        return oldItem == newItem
    }

}
