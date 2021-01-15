package com.example.getflix.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.activities.MainActivity
import com.example.getflix.addToShoppingCart
import com.example.getflix.databinding.ProductCardBinding
import com.example.getflix.models.AddressModel
import com.example.getflix.models.CartProductModel
import com.example.getflix.ui.fragments.CartFragmentDirections.Companion.actionCartFragmentToProductFragment
import com.example.getflix.ui.viewmodels.CartViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_card.view.*

class CartAdapter(
    fragment: Fragment,
    viewModel: CartViewModel
) : ListAdapter<CartProductModel, CartAdapter.RowHolder>(CartDiffCallback()) {

    val fragment = fragment
    val viewModel = viewModel

    // mutable live data for deleted item position
    val pos = MutableLiveData<Int>()

    init {
        pos.value = -1
    }

    class RowHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartProductModel: CartProductModel,viewModel: CartViewModel) {
            binding.cardproduct = cartProductModel
            val product = cartProductModel.product
            var price = product.priceDiscounted

            Picasso.get().load(product.images[0]).into(binding.cartProductImage)

            binding.shoppingCartProductAmount.text = cartProductModel.amount.toString()

            if (product.priceDiscounted.toString().length > 5) {
                binding.cartProductPrice.text =
                    product.priceDiscounted.toString().substring(0, 5) + " TL"
                price = product.priceDiscounted.toString().substring(0, 5).toDouble()
            } else {
                binding.cartProductPrice.text = product.priceDiscounted.toString() + " TL"

            }

            binding.decraseCartProduct.setOnClickListener {
                var amount: Int = binding.shoppingCartProductAmount.text.toString().toInt()
                if (amount > 1) {
                    amount = amount.dec()
                    viewModel.updateCustomerCartProduct(
                        amount,
                        cartProductModel.id,
                        product.id
                    )
                    binding.shoppingCartProductAmount.text = amount.toString()
                    val totalPrice = (price * (binding.shoppingCartProductAmount.text.toString().toInt()).toDouble()).toString()
                    if (totalPrice.length > 5) {
                        binding.totalPriceCartProduct.text = "Total : "+ totalPrice.substring(0, 5) + " TL"
                    } else {
                        binding.totalPriceCartProduct.text = "Total : "+totalPrice + " TL"

                    }
                }
            }
            binding.increaseCartProduct.setOnClickListener {
                var amount: Int = binding.shoppingCartProductAmount.text.toString().toInt()
                amount = amount.inc()
                viewModel.updateCustomerCartProduct(
                    amount,
                    cartProductModel.id,
                    product.id
                )
                binding.shoppingCartProductAmount.text = amount.toString()
                val totalPrice = (price * (binding.shoppingCartProductAmount.text.toString().toInt()).toDouble()).toString()
                if (totalPrice.length > 5) {
                    binding.totalPriceCartProduct.text = "Total : "+ totalPrice.substring(0, 5) + " TL"
                } else {
                    binding.totalPriceCartProduct.text = "Total : "+totalPrice + " TL"

                }
            }

            val totalPrice = (price * (binding.shoppingCartProductAmount.text.toString().toInt()).toDouble()).toString()
            if (totalPrice.length > 5) {
                binding.totalPriceCartProduct.text = "Total : "+ totalPrice.substring(0, 5) + " TL"
            } else {
                binding.totalPriceCartProduct.text = "Total : "+totalPrice + " TL"

            }
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
        currentList?.get(position)?.let {
            holder.bind(it,viewModel)

            holder?.itemView!!.setOnClickListener {
                fragment.view?.findNavController()
                    ?.navigate(actionCartFragmentToProductFragment(currentList?.get(position).product.id))
            }
        }
    }


    fun deleteItem(position: Int): CartProductModel {
        pos.value = position
        return currentList?.get(position)!!
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