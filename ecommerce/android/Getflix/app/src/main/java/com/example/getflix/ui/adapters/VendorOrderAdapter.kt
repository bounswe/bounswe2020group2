package com.example.getflix.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.OrderStatus
import com.example.getflix.R
import com.example.getflix.databinding.CardVendorOrderBinding
import com.example.getflix.models.VendorOrderModel
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.squareup.picasso.Picasso

class VendorOrderAdapter constructor(context: Context) :
    ListAdapter<VendorOrderModel, VendorOrderAdapter.ViewHolder>(VendorOrderDiffCallback()) {

    private val _destination = MutableLiveData<VendorOrderModel?>()
    val destination: LiveData<VendorOrderModel?>
        get() = _destination

    var fragmentNavigatorExtras: FragmentNavigator.Extras? = null

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
        _destination.value = null
    }

    fun onSelectionCompleted() {
        _selectedOrder.value = null
        _selectedStatus.value = null
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(order: VendorOrderModel) {

        val spinnerRadius = context.resources.getDimension(R.dimen.spinnerCornerSize)
        val constraintLayout = binding.constraintLayout4
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setTopLeftCorner(CornerFamily.CUT, spinnerRadius)
            .setTopRightCorner(CornerFamily.CUT, spinnerRadius)
            .setBottomLeftCorner(CornerFamily.CUT, spinnerRadius)
            .setBottomRightCorner(CornerFamily.CUT, spinnerRadius)
            .build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        shapeDrawable.setFillColor(
            ContextCompat.getColorStateList(
                context,
                R.color.vendor_background
            )
        )
        shapeDrawable.elevation = 4.5.toFloat()
        ViewCompat.setBackground(constraintLayout, shapeDrawable)



        val shapeAppearanceModelForSpinner = ShapeAppearanceModel()
            .toBuilder()
            .setTopLeftCorner(CornerFamily.CUT, spinnerRadius)
            .setTopRightCorner(CornerFamily.CUT, spinnerRadius)
            .setBottomLeftCorner(CornerFamily.CUT, spinnerRadius)
            .setBottomRightCorner(CornerFamily.CUT, spinnerRadius)
            .build()

        val shapeDrawableForSpinner = MaterialShapeDrawable(shapeAppearanceModelForSpinner)
        shapeDrawableForSpinner.setFillColor(
            ContextCompat.getColorStateList(
                context,
                R.color.white
            )
        )
        shapeDrawableForSpinner.elevation = 4.5.toFloat()
        ViewCompat.setBackground(binding.spinnerLayout, shapeDrawableForSpinner)
        if(order.product.images[0].contains("/image/"))
            Picasso.get().load("http://3.134.80.26:8000" + order.product.images[0]).into(binding.vendorOrderImage)
        else
        Picasso.get().load(order.product.images[0]).into(binding.vendorOrderImage)
        binding.purchaseDate.text = order.purchaseDate.substring(0, 10) + " " + order.purchaseDate.substring(11, 19)
        binding.vendorOrderAmount.text = order.amount.toString()
        binding.vendorOrderTotalPrice.text = order.totalPrice.toString() + " TL"
        binding.vendorOrderUnitPrice.text = order.unitPrice.toString() + " TL"
        binding.vendorOrderName.text = order.product.name

        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.order_status,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
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
                val newStatusValue = parent?.getItemAtPosition(position)
                val newStatus = when (newStatusValue) {
                    OrderStatus.ACCEPTED.value -> OrderStatus.ACCEPTED.status
                    OrderStatus.DELIVERED.value -> OrderStatus.DELIVERED.status
                    OrderStatus.AT_CARGO.value -> OrderStatus.AT_CARGO.status
                    else -> OrderStatus.CANCELLED.status
                }
                if (order.status.equals(newStatus).not()) {
                    _selectedOrder.value = order.id
                    _selectedStatus.value = order.status
                }
            }
        }
        val status = when (order.status) {
            OrderStatus.ACCEPTED.status -> OrderStatus.ACCEPTED.status
            OrderStatus.DELIVERED.status -> OrderStatus.DELIVERED.value
            OrderStatus.AT_CARGO.status -> OrderStatus.AT_CARGO.value
            else -> OrderStatus.CANCELLED.value
        }
        binding.spinner2.setSelection(
            adapter.getPosition(
                status
            )
        )
        binding.clickableLayout.setOnClickListener {
            if (it != null) {
                val extras = FragmentNavigatorExtras(
                    binding.vendorOrderImage to "big_image",
                    binding.vendorOrderUnitPrice to "fragment_price_after_discount",
                    binding.vendorOrderTotalPrice to "fragment_total_price",
                    binding.vendorOrderName to "fragment_order_name",
                    binding.purchaseDate to "fragment_order_date",
                    binding.vendorOrderAmount to "fragment_order_amount"
                )
                fragmentNavigatorExtras = extras
                _destination.value = order
            }
        }

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

