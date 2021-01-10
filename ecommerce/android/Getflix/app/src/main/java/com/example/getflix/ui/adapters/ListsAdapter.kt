package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ListItemLayoutBinding
import com.example.getflix.models.ListModel
import com.example.getflix.ui.fragments.ListsFragmentDirections

class ListsAdapter(
        private val listList: ArrayList<ListModel>?,
) : ListAdapter<ListModel, ListsAdapter.RowHolder>(ListsDiffCallback()) {

    class RowHolder(val binding: ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(list: ListModel, position: Int) {
            binding.list = list
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemLayoutBinding.inflate(layoutInflater, parent, false)
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

    override fun onBindViewHolder(holder: ListsAdapter.RowHolder, position: Int) {
        listList?.get(position)?.let {
            holder.bind(it, position)
            holder?.itemView!!.setOnClickListener {
                fragment.findNavController().navigate(
                    ListsFragmentDirections.actionFavoritesFragmentToListProductsFragment(
                        listList?.get(position)!!)
                )
            }
        }
    }


    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        listList?.get(position)?.let { holder.bind(it, position) }
    }


}

class ListsDiffCallback : DiffUtil.ItemCallback<ListModel>() {
    override fun areItemsTheSame(oldItem: ListModel, newItem: ListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListModel, newItem: ListModel): Boolean {
        return oldItem == newItem
    }

}