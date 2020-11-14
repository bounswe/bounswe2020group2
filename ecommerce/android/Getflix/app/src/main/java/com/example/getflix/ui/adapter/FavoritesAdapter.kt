package com.example.getflix.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.model.ProductModel
import kotlinx.android.synthetic.main.favproduct_layout.view.*

class FavoritesAdapter(
    private val productList: ArrayList<ProductModel>?,
) : RecyclerView.Adapter<FavoritesAdapter.RowHolder>() {

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: ProductModel, position: Int) {
            itemView.product_name.text = product.name

        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.favproduct_layout, parent, false)
                return RowHolder(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder.from(parent)
    }

    override fun getItemCount(): Int {
        if (productList != null) {
            return productList.count()
        }
        return 0;
    }


    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        productList?.get(position)?.let { holder.bind(it, position) }
    }


}