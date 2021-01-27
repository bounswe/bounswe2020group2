package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.ListProductItemBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.ListModel
import com.example.getflix.models.ListProductModel
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.fragments.ListsFragmentDirections
import com.example.getflix.ui.viewmodels.ProductViewModel
import com.squareup.picasso.Picasso

class ListProductsAdapter(
    private val listProductList: ArrayList<ListProductModel>, fragment: Fragment
) : ListAdapter<ListProductModel, ListProductsAdapter.RowHolder>(ListProductsDiffCallback()) {

    // mutable live data for deleted item position
    val pos = MutableLiveData<Int>()
    var fragment = fragment
    var productViewModel = ViewModelProvider(fragment).get(ProductViewModel::class.java)

    init {
        pos.value = -1
    }

    class RowHolder(val binding: ListProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listProduct: ListProductModel, position: Int) {
            binding.listproduct = listProduct

            if(listProduct.product.name.length>70)
                binding.cartProductName.text = listProduct.product.name.subSequence(0,70)
            else
                binding.cartProductName.text = listProduct.product.name
            binding.cartProductPrice.text = listProduct.product.price.toString()+" TL"

            if(!listProduct.product.images.isNullOrEmpty())
                if(listProduct.product.images[0].contains("/image/"))
                    Picasso.get().load("http://3.134.80.26:8000" + listProduct.product.images[0]).into(binding.cartProductImage)
                else
                Picasso.get().load(listProduct.product.images[0]).into(binding.cartProductImage)
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListProductItemBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }

    fun deleteItem(position: Int) {
        pos.value = position
    }



    fun resetPos() {
        pos.value = -1
    }


    override fun getItemCount(): Int {
        if (listProductList != null) {
            return listProductList.count()
        }
        return 0;
    }


    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        listProductList?.get(position)?.let { holder.bind(it, position)
            holder?.binding.addToCart.setOnClickListener {
                productViewModel.addCustomerCartProduct(1,
                    listProductList?.get(position)?.product.id
                )
                productViewModel.navigateBack.observe(fragment.viewLifecycleOwner, Observer{
                    if(it) {
                        doneAlert(fragment, "Product is added to your shopping cart!", null)
                        productViewModel.resetNavigate()
                    }
                })
            }
        }
    }

}

class ListProductsDiffCallback : DiffUtil.ItemCallback<ListProductModel>() {
    override fun areItemsTheSame(oldItem: ListProductModel, newItem: ListProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListProductModel, newItem: ListProductModel): Boolean {
        return oldItem == newItem
    }


}