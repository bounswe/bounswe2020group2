package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.user
import com.example.getflix.models.OrderPurchasedModel
import com.example.getflix.models.ReviewModel
import com.example.getflix.models.Status
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.ReviewRequest
import com.example.getflix.service.responses.AddReviewResponse
import com.example.getflix.service.responses.CardProUpdateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPurchasedViewModel : ViewModel()  {

    private val _purchasedProductList = MutableLiveData<MutableList<OrderPurchasedModel>>()
    val purchasedProductList: LiveData<MutableList<OrderPurchasedModel>>
        get() = _purchasedProductList

    private val _onCompleteReview = MutableLiveData<AddReviewResponse?>()
    val onCompleteReview: LiveData<AddReviewResponse?>
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

    fun addReview(reviewRequest: ReviewRequest){
        GetflixApi.getflixApiService.addReview("Bearer " + user!!.token, reviewRequest).enqueue(object :
            Callback<AddReviewResponse>{
            override fun onFailure(call: Call<AddReviewResponse>, t: Throwable) {

                _onCompleteReview.value = null
                println(t.message)
            }

            override fun onResponse(call: Call<AddReviewResponse>, response: Response<AddReviewResponse>) {
                _onCompleteReview.value = response.body()
                println("response'taaa")
                println(response.body())
            }

        })
    }

}