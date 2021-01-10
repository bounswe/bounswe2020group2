package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ListProductModel
import com.example.getflix.models.ProductModel

class ListProductViewModel : ViewModel() {

    private val _listProductList = MutableLiveData<MutableList<ListProductModel>>()
    val listList: LiveData<MutableList<ListProductModel>>
        get() = _listProductList

    fun addList(listPrdoductModel: ListProductModel) {
        if (_listProductList.value != null) {
            val lists = _listProductList.value
            lists?.add(listPrdoductModel)
            _listProductList.value = lists
        } else {
            val listProducts = arrayListOf<ListProductModel>()
            listProducts.add(listPrdoductModel)
            _listProductList.value = listProducts
        }

    }

}