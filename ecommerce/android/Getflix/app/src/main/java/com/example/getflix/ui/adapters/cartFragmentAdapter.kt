package com.example.getflix.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.acitivities.productDetailActivity

class cartFragmentAdapter : RecyclerView.Adapter<cartFragmentAdapter.ViewHolder>() {

    private val name = arrayOf("T-Shirt",
        "Skirt", "Sweater", "Jean",
        "Chair", "Table", "Desk",
        "Notebook")

    private val price = arrayOf("30$", "40$",
        "30$", "50$",
        "60$", "70$",
        "60$", "10$")

    private val brandId = arrayOf("GAP",
        "GAP", "MAVI", "MAVI",
        "IKEA", "IKEA", "IKEA",
        "NEZIH")

    private val description = arrayOf("Blue T-Shirt",
        "Blue Skirt", "Blue Sweater", "Blue Jean",
        "Blue Chair", "Blue Table", "Blue Desk",
        "Blue Notebook")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var price: TextView
        var brandId: TextView
        var description: TextView

        init {
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.price)
            brandId = itemView.findViewById(R.id.brand)
            description = itemView.findViewById(R.id.description)

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, productDetailActivity::class.java).apply {
                    putExtra("name", name.text)
                    putExtra("price", price.text)
                    putExtra("brandId", brandId.text)
                    putExtra("description", description.text)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.product_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.name.text = name[i]
        viewHolder.price.text = price[i]
        viewHolder.brandId.text = brandId[i]
        viewHolder.description.text = description[i]

    }

    override fun getItemCount(): Int {
        return name.size
    }
}