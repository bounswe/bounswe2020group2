package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ProductModel

class CartViewModel : ViewModel() {

    private val _productList = MutableLiveData<MutableList<ProductModel>>()
    val productList: LiveData<MutableList<ProductModel>>
        get() = _productList

    fun addProduct(productModel: ProductModel) {
        if (_productList.value != null) {
            val products = _productList.value
            products?.add(productModel)
            _productList.value = products
        } else {
            val products = arrayListOf<ProductModel>()
            products.add(productModel)
            _productList.value = products
        }
    }


}