package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.AddressCardItemBinding
import com.example.getflix.databinding.ProductCardBinding
import com.example.getflix.models.AddressModel
import com.example.getflix.models.ProductModel


class AddressAdapter(
    private val addressList: ArrayList<AddressModel>?,
) : ListAdapter<AddressModel, AddressAdapter.RowHolder>(AddressDiffCallback()) {


    class RowHolder(val binding: AddressCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: AddressModel, position: Int) {
            binding.title.text = address.title
            binding.addressInfo.text =address.address
            binding.city.text=address.province
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AddressCardItemBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        addressList?.get(position)?.let { holder.bind(it, position) }
    }


}

class AddressDiffCallback : DiffUtil.ItemCallback<AddressModel>() {

    override fun areItemsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
        return oldItem == newItem
    }

}