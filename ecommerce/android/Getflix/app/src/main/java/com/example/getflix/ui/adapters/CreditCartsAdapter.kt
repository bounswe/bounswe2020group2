package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.CreditCardItemBinding
import com.example.getflix.models.CardModel
import com.example.getflix.ui.fragments.AddressFragmentDirections
import com.example.getflix.ui.fragments.BankAccountFragment
import com.example.getflix.ui.fragments.BankAccountFragmentDirections


class CreditCartsAdapter(
    private val creditCartsList: ArrayList<CardModel>?, fragment: BankAccountFragment
) : ListAdapter<CardModel, CreditCartsAdapter.RowHolder>(CardDiffCallback()) {

    // mutable live data for deleted item position
    val pos = MutableLiveData<Int>()
    val fragment = fragment

    init {
        pos.value = -1
    }


    class RowHolder(val binding: CreditCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(credit: CardModel, position: Int) {
            binding.name.text = credit.name
            binding.ownerName.text ="Owner: " + credit.owner_name
            binding.serialNum.text = credit.serial_number
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CreditCardItemBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }


    override fun getItemCount(): Int {

        return creditCartsList!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCartsAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        creditCartsList?.get(position)?.let {
            holder.bind(it, position)
            holder?.itemView!!.setOnClickListener {
                fragment.findNavController().navigate(BankAccountFragmentDirections.actionBankAccountFragmentToUpdateCreditCardFragment(
                    creditCartsList?.get(position)!!)
                )
            }
        }
    }

    fun deleteItem(position: Int): CardModel {
        pos.value = position
        /*creditCartsList!!.removeAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, creditCartsList!!.size);*/
        return creditCartsList?.get(position)!!
    }

    fun resetPos() {
        pos.value = -1
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