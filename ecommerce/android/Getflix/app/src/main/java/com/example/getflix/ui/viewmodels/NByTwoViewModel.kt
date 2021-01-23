package com.example.getflix.ui.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ProductModel
import com.example.getflix.models.VendorModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.responses.ProSearchByVendorResponse
import com.example.getflix.vendorModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NByTwoViewModel: ViewModel() {

    private val _products = MutableLiveData<List<ProductModel>?>()
    val products: LiveData<List<ProductModel>?>
        get() = _products

    init {
        println(vendorModel)
        getProductsOfVendor()
    }
    fun getProductsOfVendor() {
        GetflixApi.getflixApiService.searchProductsByVendor(vendorModel!!.id)
            .enqueue(object :
                Callback<ProSearchByVendorResponse> {
                override fun onFailure(call: Call<ProSearchByVendorResponse>, t: Throwable) {
                    _products.value = null
                    println("RESPONSE DÖNMÜYOR")
                }

                override fun onResponse(
                    call: Call<ProSearchByVendorResponse>,
                    response: Response<ProSearchByVendorResponse>
                ) {
                    if (response.body() != null) {
                        println("Response döndü")
                        _products.value = response.body()!!.data.products
                    } else {
                        println("RESPONSE NULL")
                        _products.value = null
                    }

                }
            }
            )

    }

}