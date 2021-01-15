package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.OrderModel
import com.example.getflix.models.ProductModel

class OrderViewModel : ViewModel() {

    private val _orderList = MutableLiveData<MutableList<OrderModel>>()
    val orderList: LiveData<MutableList<OrderModel>>
        get() = _orderList

    fun addProduct(orderModel: OrderModel) {
        if (_orderList.value != null) {
            val orders = _orderList.value
            orders?.add(orderModel)
            _orderList.value = orders
        } else {
            val orders = arrayListOf<OrderModel>()
            orders.add(orderModel)
            _orderList.value = orders
        }
    }


}