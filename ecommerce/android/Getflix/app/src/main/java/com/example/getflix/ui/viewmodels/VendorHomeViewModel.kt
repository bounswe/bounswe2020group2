package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.ProductModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.*
import com.example.getflix.service.responses.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorHomeViewModel : ViewModel() {

    private val _productList = MutableLiveData<MutableList<ProductModel>>()
    val productList: LiveData<MutableList<ProductModel>>
        get() = _productList

    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean>
        get() = _navigateBack

    fun updateVendorProduct(updateRequest: VendorProUpdateRequest) {
        GetflixApi.getflixApiService.updateVendorProduct(
            "Bearer " + MainActivity.StaticData.user!!.token, updateRequest
        )
            .enqueue(object :
                Callback<VendorProUpdateResponse> {
                override fun onFailure(call: Call<VendorProUpdateResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<VendorProUpdateResponse>,
                    response: Response<VendorProUpdateResponse>
                ) {
                    if (response.code() == 200) {
                        _navigateBack.value = true

                    }
                    // searchByVendor(3)
                }
            }
            )
    }

    fun addVendorProduct(addRequest: AddProductRequest) {
        GetflixApi.getflixApiService.addProduct(
            "Bearer " + MainActivity.StaticData.user!!.token, addRequest
        )
            .enqueue(object :
                Callback<AddProductResponse> {
                override fun onFailure(call: Call<AddProductResponse>, t: Throwable) {
                    println("failure")
                }

                override fun onResponse(
                    call: Call<AddProductResponse>,
                    response: Response<AddProductResponse>
                ) {
                    println(response.code())
                    println(response.body().toString())
                    if (response.code()==200) {
                        _navigateBack.value = true
                        println(response.body()!!.status.message)
                    }

                    // searchByVendor(3)
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
                    if(response.body()!=null && response.body()!!.data.products.isNotEmpty()) {
                        _productList.value =
                            response.body()!!.data.products as MutableList<ProductModel>
                    }

                }
            }
            )
    }

    fun resetNavigate() {
        _navigateBack.value = false
    }
}