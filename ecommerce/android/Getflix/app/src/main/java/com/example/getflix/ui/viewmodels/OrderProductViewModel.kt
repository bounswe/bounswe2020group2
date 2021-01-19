package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.OrderPurchasedModel

class OrderProductViewModel : ViewModel()  {

    private val _purchasedProductList = MutableLiveData<MutableList<OrderPurchasedModel>>()
    val purchasedProductList: LiveData<MutableList<OrderPurchasedModel>>
        get() = _purchasedProductList

    fun addList(listPrdoductModel: OrderPurchasedModel) {
        if (_purchasedProductList.value != null) {
            val purchasedProducts = _purchasedProductList.value
            purchasedProducts?.add(listPrdoductModel)
            _purchasedProductList.value = purchasedProducts
        } else {
            val purchasedProducts = arrayListOf<OrderPurchasedModel>()
            purchasedProducts.add(listPrdoductModel)
            _purchasedProductList.value = purchasedProducts
        }

    }

}