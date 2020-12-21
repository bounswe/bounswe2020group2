package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ProductModel

class SubCategoryViewModel : ViewModel() {

    private val _productList = MutableLiveData<MutableList<ProductModel>>()
    val productList: LiveData<MutableList<ProductModel>>
        get() = _productList

    private val _sortBy = MutableLiveData<String>()
    val sortBy: LiveData<String>
        get() = _sortBy

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

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

    fun sort(sortS: String?) {
        _sortBy.value = sortS
        if (sortS == "Price")
            sortPrice()
    }

    private fun sortPrice() {
        val sorted: MutableList<ProductModel> = _productList.value!!.sortedBy { it.price.toInt() } as MutableList<ProductModel>
        _productList.value = sorted
    }
}