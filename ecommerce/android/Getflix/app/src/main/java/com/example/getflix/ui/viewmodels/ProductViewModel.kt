package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ProductModel
import com.example.getflix.service.GetflixApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel:  ViewModel() {
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


    private val _product = MutableLiveData<ProductModel?>()
    val product: LiveData<ProductModel?>
        get() = _product

    init {
        _amount.value = 1
        _isLiked.value = false
        _product.value = null
    }


    fun getProduct(productId: Int) {
        GetflixApi.getflixApiService.getProduct(productId)
                .enqueue(object :
                        Callback<ProductModel> {
                    override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                        _product.value = null
                    }

                    override fun onResponse(
                            call: Call<ProductModel>,
                            response: Response<ProductModel>
                    ) {
                        _product.value = response.body()

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