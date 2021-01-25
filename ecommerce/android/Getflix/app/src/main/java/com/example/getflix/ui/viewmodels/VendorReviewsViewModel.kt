package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ProductModel
import com.example.getflix.models.ProductReviewListModel
import com.example.getflix.models.ReviewModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.ProSearchByVendorRequest
import com.example.getflix.service.responses.ProSearchByVendorResponse
import com.example.getflix.vendorModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorReviewsViewModel : ViewModel() {
    private val _products = MutableLiveData<List<ProductModel>>()
    val products: LiveData<List<ProductModel>?>
        get() = _products

    private val _comments = MutableLiveData<MutableList<ReviewModel>>()
    val comments: LiveData<MutableList<ReviewModel>>
        get() = _comments


    private var  currentReviews : MutableList<ReviewModel>
    init {
        _comments.value = mutableListOf()
        currentReviews = mutableListOf()
        getProductsOfVendor()
    }

    private fun getProductsOfVendor() {
        GetflixApi.getflixApiService.searchProductsByVendor(ProSearchByVendorRequest(vendorModel!!.id))
            .enqueue(object :
                Callback<ProSearchByVendorResponse> {
                override fun onFailure(call: Call<ProSearchByVendorResponse>, t: Throwable) {
                    _products.value = null
                }

                override fun onResponse(
                    call: Call<ProSearchByVendorResponse>,
                    response: Response<ProSearchByVendorResponse>
                ) {
                    if (response.body() != null) {
                        _products.value = response.body()!!.data.products
                    } else {
                        _products.value = null
                    }

                }
            }
            )

    }

    fun getAllReviewsOfVendor() {
        if (_products.value != null) {
            /*
            for (product in _products.value!!) {
                getProductReviews(product.id)
            }*/
            getProductReviews(_products.value!!.get(0).id)
        }
    }

    private fun getProductReviews(productId: Int) {

        GetflixApi.getflixApiService.getReviewOfProduct(productId)
            .enqueue(object :
                Callback<ProductReviewListModel> {
                override fun onFailure(call: Call<ProductReviewListModel>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ProductReviewListModel>,
                    response: Response<ProductReviewListModel>
                ) {
                    if (response.body() != null && response.body()!!.reviews != null) {
                        println(response.body()!!.reviews!!)
                        _comments.value = (response.body()!!.reviews as MutableList<ReviewModel>?)!!
                    }
                }
            }
            )
    }
    
}