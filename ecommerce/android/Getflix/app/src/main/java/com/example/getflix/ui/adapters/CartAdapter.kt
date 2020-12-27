package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ProductCardBinding
import com.example.getflix.models.CartProductModel
import com.example.getflix.ui.fragments.CartFragmentDirections.Companion.actionCartFragmentToProductFragment
import kotlinx.android.synthetic.main.product_card.view.*

class CartAdapter(
    private val productList: MutableList<CartProductModel>, fragment: Fragment
) : ListAdapter<CartProductModel, CartAdapter.RowHolder>(CartDiffCallback()) {

    val fragment = fragment

    class RowHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: CartProductModel, position: Int) {
            binding.cardproduct = product
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductCardBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        productList?.get(position)?.let {
            holder.bind(it, position)
            holder.binding.decrease.setOnClickListener {
                holder.binding.integerAmount.text = (holder.binding.integerAmount.text.toString().toInt()-1).toString()
            }
            holder.binding.increase.setOnClickListener {
                holder.binding.integerAmount.text = (holder.binding.integerAmount.text.toString().toInt()+1).toString()
            }
            holder?.itemView!!.setOnClickListener{
                fragment.view?.findNavController()?.navigate(actionCartFragmentToProductFragment(productList?.get(position).product.id))
            }
        }
    }

    override fun getItemCount(): Int {
        return productList!!.count()
    }


}

class CartDiffCallback : DiffUtil.ItemCallback<CartProductModel>() {
    override fun areItemsTheSame(oldItem: CartProductModel, newItem: CartProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartProductModel, newItem: CartProductModel): Boolean {
        return oldItem == newItem
    }

}