package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.CartProductModel
import com.example.getflix.models.ProductModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.CustomerCheckoutRequest
import com.example.getflix.service.responses.CustomerCheckoutResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {

    private val _cardProducts = MutableLiveData<MutableList<CartProductModel>>()
    val cardProducts: LiveData<MutableList<CartProductModel>>
        get() = _cardProducts

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }



    /*fun addProduct(productModel: ProductModel) {
        if (_productList.value != null) {
            val products = _productList.value
            products?.add(productModel)
            _productList.value = products
        } else {
            val products = arrayListOf<ProductModel>()
            products.add(productModel)
            _productList.value = products
        }
    }*/

    fun getCustomerCartProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerAllCartProducts("Bearer " + MainActivity.StaticData.user!!.token,
                MainActivity.StaticData.user!!.id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    println("succesfull mu")
                    response.body().let { it ->
                        _cardProducts.value = response.body()!!.cartProducts as MutableList<CartProductModel>
                        println(_cardProducts.value.toString())
                    }
                }
            }
        }
    }


}