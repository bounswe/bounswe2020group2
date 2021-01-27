package com.example.getflix.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.CardProductBinding
import com.example.getflix.databinding.VendorProductBinding
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.fragments.AddressFragmentDirections
import com.example.getflix.ui.fragments.VendorHomeFragmentDirections
import com.example.getflix.ui.viewmodels.HomeViewModel
import com.squareup.picasso.Picasso

class VendorHomeProductsAdapter(
    private val productList: MutableList<ProductModel>, fragment: Fragment
) : ListAdapter<ProductModel, VendorHomeProductsAdapter.RowHolder>(VendorHomeProductsDiffCallback()) {

    val fragment = fragment

    class RowHolder(val binding: VendorProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {
            if(product.images.isNotEmpty())

                if(product.images[0].contains("/image/"))
                    Picasso.get().load("http://3.134.80.26:8000" + product.images[0]).into(binding.productImage)
                else
            Picasso.get().load(product.images[0]).into(binding.productImage)
            binding.product = product
            if (product.price.toString().length > 5) {
                binding.oldProductPrice.text = product.price.toString().substring(0, 5) + " TL"
            } else {
                binding.oldProductPrice.text = product.price.toString() + " TL"

            }
            binding.oldProductPrice.setPaintFlags(binding.oldProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            if (product.priceDiscounted.toString().length > 5) {
                binding.productPrice.text =
                    product.priceDiscounted.toString().substring(0, 5) + " TL"

            } else {
                binding.productPrice.text = product.priceDiscounted.toString() + " TL"

            }
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VendorProductBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return productList.count()

    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        productList?.get(position)?.let {
            holder.bind(it, position)
            holder?.itemView!!.setOnClickListener {
                fragment.findNavController().navigate(
                    VendorHomeFragmentDirections.actionVendorHomeToUpdateProductFragment(
                        productList?.get(position)!!)
                )
            }
        }
    }


}

class VendorHomeProductsDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}