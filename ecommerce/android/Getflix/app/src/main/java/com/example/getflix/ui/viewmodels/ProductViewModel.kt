package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ProductModel
import com.example.getflix.services.GetflixApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val _recommendedProducts = MutableLiveData<MutableList<ProductModel>>()
    val recommendedProducts: LiveData<MutableList<ProductModel>>
        get() = _recommendedProducts

    private val _imageURLs = MutableLiveData<MutableList<String>>()
    val imageURLs: LiveData<MutableList<String>>
        get() = _imageURLs

    private val _amount = MutableLiveData<Int>()
    val amount: LiveData<Int>
        get() = _amount

    private val _isLiked = MutableLiveData<Boolean>()
    val isLiked: LiveData<Boolean>
        get() = _isLiked

    private var _productId :Int?  = null

    private val _product = MutableLiveData<ProductModel>()
    val product: LiveData<ProductModel>
        get() = _product

    init {
        _amount.value = 1
        _isLiked.value = false
        _productId = 1


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

        _recommendedProducts.value = mutableListOf(zaraDress2, zaraDress3, zaraJacket1, zaraJacket2, zaraSkirt1)
        _imageURLs.value = mutableListOf<String>("https://upload.wikimedia.org/wikipedia/commons/0/09/Solid_yellow.svg","https://tr.m.wikipedia.org/wiki/Jack_London#/media/Dosya%3AJackLondon.jpg")
    }

    fun setProduct(productId: Int){
        _productId = productId
    }

    fun getProduct() {
        GetflixApi.getflixApiService.getProduct(20)
                .enqueue(object :
                        Callback<ProductModel> {
                    override fun onFailure(call: Call<ProductModel>, t: Throwable) {

                    }

                    override fun onResponse(
                            call: Call<ProductModel>,
                            response: Response<ProductModel>
                    ) {
                        println(response.body().toString())
                        println(response.code())

                    }
                }
                )
    }

    fun decreaseAmount() {
        if(_amount.value!! > 1) {
            _amount.value = _amount.value?.dec()
        }
    }

    fun increaseAmount() {

        _amount.value = _amount.value?.inc()
    }
    fun onLikeClick(){
        _isLiked.value = _isLiked.value?.not()
    }
}