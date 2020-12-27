package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.AddressCardItemBinding
import com.example.getflix.models.AddressModel
import com.example.getflix.ui.fragments.AddressFragment
import com.example.getflix.ui.fragments.AddressFragmentDirections

class AddressAdapter(
    private val addressList: ArrayList<AddressModel>?,
     fragment: AddressFragment
) : ListAdapter<AddressModel, AddressAdapter.RowHolder>(AddressDiffCallback()) {

    // mutable live data for deleted item position
    val pos = MutableLiveData<Int>()
    val fragment = fragment

    init {
        pos.value = -1
    }


    class RowHolder(val binding: AddressCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: AddressModel, position: Int) {
            binding.title.text = address.title
            binding.addressInfo.text =address.address
            binding.city.text=address.city
            binding.province.text = address.province
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


    override fun getItemCount(): Int {
        super.getItemCount()
        return addressList!!.size
    }


    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        addressList?.get(position)?.let {
            holder.bind(it, position)
            holder?.itemView!!.setOnClickListener {
                fragment.findNavController().navigate(
                    AddressFragmentDirections.actionAddressFragmentToUpdateAddressFragment(
                    addressList?.get(position)!!)
                )
            }
        }
    }

    fun deleteItem(position: Int): AddressModel {
        pos.value = position
        return addressList?.get(position)!!
    }

    fun resetPos() {
        pos.value = -1
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