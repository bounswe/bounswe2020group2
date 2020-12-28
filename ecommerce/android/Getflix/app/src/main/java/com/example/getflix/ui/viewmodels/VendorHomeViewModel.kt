package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ProductModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.ProSearchBySubcategoryRequest
import com.example.getflix.service.requests.ProSearchByVendorRequest
import com.example.getflix.service.responses.ProSearchBySubcategoryResponse
import com.example.getflix.service.responses.ProSearchByVendorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorHomeViewModel: ViewModel() {

    private val _productList = MutableLiveData<MutableList<ProductModel>>()
    val productList: LiveData<MutableList<ProductModel>>
        get() = _productList

    fun searchBySubcategory(subId: Int) {
        GetflixApi.getflixApiService.searchProductsBySubcategory(ProSearchBySubcategoryRequest(subId))
            .enqueue(object :
                Callback<ProSearchBySubcategoryResponse> {
                override fun onFailure(call: Call<ProSearchBySubcategoryResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ProSearchBySubcategoryResponse>,
                    response: Response<ProSearchBySubcategoryResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    _productList.value = response.body()!!.data.products as MutableList<ProductModel>

                }
            }
            )
    }

    fun searchByVendor(vendorId: Int) {
        GetflixApi.getflixApiService.searchProductsByVendor(ProSearchByVendorRequest(vendorId))
            .enqueue(object :
                Callback<ProSearchByVendorResponse> {
                override fun onFailure(call: Call<ProSearchByVendorResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ProSearchByVendorResponse>,
                    response: Response<ProSearchByVendorResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    _productList.value = response.body()!!.data.products as MutableList<ProductModel>

                }
            }
            )
    }
}