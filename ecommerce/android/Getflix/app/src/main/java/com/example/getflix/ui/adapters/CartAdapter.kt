package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ProductCardBinding
import com.example.getflix.models.AddressModel
import com.example.getflix.models.CartProductModel
import com.example.getflix.ui.fragments.CartFragmentDirections.Companion.actionCartFragmentToProductFragment
import com.example.getflix.ui.viewmodels.CartViewModel
import kotlinx.android.synthetic.main.product_card.view.*

class CartAdapter(
    private val productList: MutableList<CartProductModel>, fragment: Fragment, viewModel: CartViewModel
) : ListAdapter<CartProductModel, CartAdapter.RowHolder>(CartDiffCallback()) {

    val fragment = fragment
    val viewModel = viewModel
    // mutable live data for deleted item position
    val pos = MutableLiveData<Int>()

    init {
        pos.value = -1
    }

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
                //holder.binding.integerAmount.text = (holder.binding.integerAmount.text.toString().toInt()-1).toString()
                viewModel.updateCustomerCartProduct(holder.binding.integerAmount.text.toString().toInt()-1,productList?.get(position).id,productList?.get(position).product.id)
            }
            holder.binding.increase.setOnClickListener {
                //holder.binding.integerAmount.text = (holder.binding.integerAmount.text.toString().toInt()+1).toString()
                viewModel.updateCustomerCartProduct(holder.binding.integerAmount.text.toString().toInt()+1,productList?.get(position).id,productList?.get(position).product.id)
            }
            holder?.itemView!!.setOnClickListener{
                fragment.view?.findNavController()?.navigate(actionCartFragmentToProductFragment(productList?.get(position).product.id))
            }
        }
    }

    override fun getItemCount(): Int {
        return productList!!.count()
    }

    fun deleteItem(position: Int): CartProductModel {
        pos.value = position
        return productList?.get(position)!!
    }

    fun resetPos() {
        pos.value = -1
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