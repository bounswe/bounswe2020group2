package com.example.getflix.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.OrderStatus
import com.example.getflix.R
import com.example.getflix.databinding.CardVendorOrderBinding
import com.example.getflix.models.VendorOrderModel
import com.squareup.picasso.Picasso

class VendorOrderAdapter constructor(context: Context) :
    ListAdapter<VendorOrderModel, VendorOrderAdapter.ViewHolder>(VendorOrderDiffCallback()) {

    private val _selectedOrder = MutableLiveData<Int?>()
    val selectedOrder: LiveData<Int?>
        get() = _selectedOrder

    private val _selectedStatus = MutableLiveData<String?>()
    val selectedStatus: LiveData<String?>
        get() = _selectedStatus

    private var context: Context

    init {
        _selectedStatus.value = null
        _selectedOrder.value = null
        this.context = context
    }
    fun onSelectionCompleted(){
        _selectedOrder.value = null
        _selectedStatus.value = null
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(order: VendorOrderModel) {

        Picasso.get().load(order.product.images[0]).into(binding.vendorOrderImage)
        binding.purchaseDate.text = order.purchaseDate
        binding.vendorOrderAmount.text = order.amount.toString()
        binding.vendorOrderTotalPrice.text = order.totalPrice.toString()
        binding.vendorOrderUnitPrice.text = order.unitPrice.toString()
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.order_status,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = adapter
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                _selectedStatus.value = order.status
                _selectedOrder.value = order.id
            }
        }
        val status = when (order.status) {
            OrderStatus.ACCEPTED.status -> OrderStatus.ACCEPTED.value
            OrderStatus.DELIVERED.status -> OrderStatus.DELIVERED.value
            OrderStatus.AT_CARGO.status -> OrderStatus.AT_CARGO.value
            else -> OrderStatus.CANCELLED.value
        }
        binding.spinner2.setSelection(
            adapter.getPosition(
                status
            )
        )

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CardVendorOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    CardVendorOrderBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}


class VendorOrderDiffCallback : DiffUtil.ItemCallback<VendorOrderModel>() {
    override fun areItemsTheSame(oldItem: VendorOrderModel, newItem: VendorOrderModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VendorOrderModel, newItem: VendorOrderModel): Boolean {
        return oldItem == newItem
    }

}

