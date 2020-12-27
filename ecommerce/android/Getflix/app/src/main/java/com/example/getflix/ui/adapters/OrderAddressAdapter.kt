package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.CardAddressBinding
import com.example.getflix.models.AddressModel
import com.example.getflix.ui.viewmodels.CompleteOrderViewModel


class OrderAddressAdapter :
    ListAdapter<AddressModel, OrderAddressAdapter.ViewHolder>(OrderAddressDiffCallback()){


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(address: AddressModel) {
        binding.addressTitle.text= address.title
        binding.userAddress.text = address.province +" "+ address.zipCode  +" "+ address.city  +" "+ address.country
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context,
                "selected item: " + address.title,
                Toast.LENGTH_SHORT).show()
        }
        binding.root.setOnClickListener {
            CompleteOrderViewModel.currentAddress.value = address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)

    }

    class ViewHolder private constructor(val binding: CardAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardAddressBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}

class OrderAddressDiffCallback : DiffUtil.ItemCallback<AddressModel>() {
    override fun areItemsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
        return oldItem == newItem
    }

}