package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.CardCreditCardBinding
import com.example.getflix.models.CardModel
import com.example.getflix.ui.viewmodels.CompleteOrderViewModel


class CreditCardAdapter :
    ListAdapter<CardModel, CreditCardAdapter.ViewHolder>(CardModelDiffCallback()){


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(cardModel: CardModel) {
        binding.creditCartName.text= cardModel.name
        binding.userCreditCard.text = "Owner Name:\n" + cardModel.owner_name + "\nSerial number:\n**** **** **** " + cardModel.serial_number.takeLast(4)
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context,
                "selected item: " + cardModel.name,
                Toast.LENGTH_SHORT).show()
        }
        binding.root.setOnClickListener {
            CompleteOrderViewModel.currentCreditCard.value = cardModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)

    }

    class ViewHolder private constructor(val binding: CardCreditCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardCreditCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}

class CardModelDiffCallback : DiffUtil.ItemCallback<CardModel>() {
    override fun areItemsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem == newItem
    }

}