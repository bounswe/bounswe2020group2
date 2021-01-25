package com.example.getflix.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.activities.MainActivity
import com.example.getflix.addToShoppingCart
import com.example.getflix.databinding.CardHomeRecommendedProductBinding
import com.example.getflix.databinding.CardProductBinding
import com.example.getflix.databinding.SubproductLayoutBinding
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.fragments.CartFragmentDirections
import com.example.getflix.ui.fragments.SubcategoryFragmentDirections
import com.squareup.picasso.Picasso

class SubCategoryAdapter(
    private val productList: MutableList<ProductModel>, fragment: Fragment
) : ListAdapter<ProductModel, SubCategoryAdapter.RowHolder>(SubcategoryDiffCallback()) {

    val fragment = fragment

    class RowHolder(val binding: SubproductLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {
            //binding.product = product
                if(product.images[0].contains("/image/"))
                   Picasso.get().load("http://3.134.80.26:8000" + product.images[0]).into(binding.productImage)
                else
                Picasso.get().load(product.images[0]).into(binding.productImage)
            if(product.name.length>70) {
                println("burdasın")
                println(product.name.length)
                binding.productName.text = product.name.substring(0, 70)
            }

            //var amount = binding.amountRecProduct.text.toString().toInt()
            binding.rating.rating = product.rating.toFloat()

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
                val binding = SubproductLayoutBinding.inflate(layoutInflater, parent, false)
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
            holder?.itemView!!.setOnClickListener{
                fragment.view?.findNavController()?.navigate(
                    SubcategoryFragmentDirections.actionSubcategoryFragmentToProductFragment(
                        productList?.get(position).id
                    )
                )
            }
        }
    }


}

class SubcategoryDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

}