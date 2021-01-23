package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ListItemLayoutBinding
import com.example.getflix.models.ListModel
import com.example.getflix.ui.fragments.AddressFragment
import com.example.getflix.ui.fragments.ListsFragment
import com.example.getflix.ui.fragments.ListsFragmentDirections

class ListsAdapter(
    private val listList: ArrayList<ListModel>?,
    fragment: ListsFragment
) : ListAdapter<ListModel, ListsAdapter.RowHolder>(ListsDiffCallback()) {

    // mutable live data for deleted item position
    val pos = MutableLiveData<Int>()
    val fragment = fragment

    init {
        pos.value = -1
    }

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

    override fun getItemCount(): Int {
        if (listList != null) {
            return listList.count()
        }
        return 0;
    }

    override fun onBindViewHolder(holder: ListsAdapter.RowHolder, position: Int) {
        listList?.get(position)?.let {
            holder.bind(it, position)
            holder?.itemView!!.setOnClickListener {
                fragment.findNavController().navigate(
                    ListsFragmentDirections.actionListsFragmentToListProductsFragment(
                        listList?.get(position)!!.products.toTypedArray())
                )
            }
        }
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