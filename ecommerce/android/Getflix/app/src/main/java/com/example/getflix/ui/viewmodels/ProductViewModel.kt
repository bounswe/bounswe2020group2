package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ProductModel

class ProductViewModel : ViewModel() {
    private val _recommendedProducts = MutableLiveData<MutableList<ProductModel>>()
    val recommendedProducts: LiveData<MutableList<ProductModel>>
        get() = _recommendedProducts

    private val _amount = MutableLiveData<Int>()
    val amount: LiveData<Int>
        get() = _amount
    init{
        _amount.value = 1


            var zaraJacket1 =
                    ProductModel(1, "Jacket", "222", "1", "Zara", 1, 1, 1, "Nice jacket", "1", "1", "1", "1")
            var zaraJacket2 =
                    ProductModel(2, "Jacket", "231", "1", "Zara", 1, 1, 1, "Cool jacket", "1", "1", "1", "1")
            var zaraJacket3 =
                    ProductModel(3, "Jacket", "321", "1", "Zara", 1, 1, 1, "Amazing jacket", "1", "1", "1", "1")
            var zaraSkirt1 =
                    ProductModel(4, "Skirt", "79", "1", "Zara", 1, 1, 1, "Nice skirt", "1", "1", "1", "1")
            var zaraSkirt2 =
                    ProductModel(5, "Skirt", "93", "1", "Zara", 1, 1, 1, "Cool skirt", "1", "1", "1", "1")
            var zaraSkirt3 =
                    ProductModel(6, "Skirt", "102", "1", "Zara", 1, 1, 1, "Amazing skirt", "1", "1", "1", "1")
            var zaraDress1 =
                    mutableListOf(7, "Dress", "60", "1", "Zara", 1, 1, 1, "Nice dress", "1", "1", "1", "1")
            var zaraDress2 =
                    ProductModel(8, "Dress", "142", "1", "Zara", 1, 1, 1, "Cool dress", "1", "1", "1", "1")
            var zaraDress3 =
                    ProductModel(9, "Dress", "201", "1", "Zara", 1, 1, 1, "Amazing dress", "1", "1", "1", "1")

        _recommendedProducts.value = mutableListOf(zaraDress2,zaraDress3,zaraJacket1,zaraJacket2,zaraSkirt1)


    }
    fun decreaseAmount() {
        _amount.value = _amount.value?.dec()
    }
    fun increaseAmount() {
        _amount.value = _amount.value?.inc()
    }
}