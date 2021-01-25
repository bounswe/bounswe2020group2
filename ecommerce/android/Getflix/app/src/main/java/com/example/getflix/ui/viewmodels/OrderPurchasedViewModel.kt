package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity.StaticData.user
import com.example.getflix.models.OrderPurchasedModel
import com.example.getflix.models.ReviewModel
import com.example.getflix.models.Status
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.ReviewRequest
import com.example.getflix.service.responses.CardProUpdateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPurchasedViewModel : ViewModel()  {

    private val _purchasedProductList = MutableLiveData<MutableList<OrderPurchasedModel>>()
    val purchasedProductList: LiveData<MutableList<OrderPurchasedModel>>
        get() = _purchasedProductList

    private val _onCompleteReview = MutableLiveData<Status?>()
    val onCompleteReview: LiveData<Status?>
        get() = _onCompleteReview

    init {
        _onCompleteReview.value = null
    }
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

    fun addReview(productId : Int, vendorId:Int, rating : Int, comment : String){
        val reviewRequest = ReviewRequest(user!!.id,productId,vendorId,rating,comment)
        GetflixApi.getflixApiService.addReview(reviewRequest).enqueue(object :
            Callback<Status>{
            override fun onFailure(call: Call<Status>, t: Throwable) {
                _onCompleteReview.value = null
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                _onCompleteReview.value = response.body()
            }

        })
    }

}