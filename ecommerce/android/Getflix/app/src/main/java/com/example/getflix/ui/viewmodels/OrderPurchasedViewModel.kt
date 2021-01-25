package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.OrderPurchasedModel

class OrderPurchasedViewModel : ViewModel()  {

    private val _purchasedProductList = MutableLiveData<MutableList<OrderPurchasedModel>>()
    val purchasedProductList: LiveData<MutableList<OrderPurchasedModel>>
        get() = _purchasedProductList

    fun addOrderPurchased(purchasedPrdoductModel: OrderPurchasedModel) {
        if (_purchasedProductList.value != null) {
            val purchasedProducts = _purchasedProductList.value
            purchasedProducts?.add(purchasedPrdoductModel)
            _purchasedProductList.value = purchasedProducts
        } else {
            val purchasedProducts = arrayListOf<OrderPurchasedModel>()
            purchasedProducts.add(purchasedPrdoductModel)
            _purchasedProductList.value = purchasedProducts
        }

    }

}