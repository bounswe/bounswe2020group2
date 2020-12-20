package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.AddressCardItemBinding
import com.example.getflix.databinding.CreditCardItemBinding
import com.example.getflix.databinding.ProductCardBinding
import com.example.getflix.models.AddressModel
import com.example.getflix.models.CardModel
import com.example.getflix.models.ProductModel


class CreditCartsAdapter(
        private val creditCartsList: ArrayList<CardModel>?,
) : ListAdapter<CardModel, CreditCartsAdapter.RowHolder>(CardDiffCallback()) {


    class RowHolder(val binding: CreditCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(credit: CardModel, position: Int) {
            binding.name.text = credit.name
            binding.ownerName.text =credit.owner_name
            binding.serialNum.text = credit.serial_number.toString()
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CreditCardItemBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCartsAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        creditCartsList?.get(position)?.let { holder.bind(it, position) }
    }


}

class CardDiffCallback : DiffUtil.ItemCallback<CardModel>() {

    override fun areItemsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem == newItem
    }

}