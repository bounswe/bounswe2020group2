package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.AddressModel
import com.example.getflix.models.ProductModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.ProSearchByQueryRequest
import com.example.getflix.service.requests.ProSearchBySubcategoryRequest
import com.example.getflix.service.requests.ProSearchSortRequest
import com.example.getflix.service.responses.ProSearchBySubcategoryResponse
import com.example.getflix.service.responses.ProSearchByVendorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoryViewModel : ViewModel() {

    private val _productList = MutableLiveData<MutableList<ProductModel>>()
    val productList: LiveData<MutableList<ProductModel>>
        get() = _productList

    private val _sortBy = MutableLiveData<String>()
    val sortBy: LiveData<String>
        get() = _sortBy

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun addProduct(productModel: ProductModel) {
        if (_productList.value != null) {
            val products = _productList.value
            products?.add(productModel)
            _productList.value = products
        } else {
            val products = arrayListOf<ProductModel>()
            products.add(productModel)
            _productList.value = products
        }

    }



    fun sort(subId: Int?,sortBy: String, sortOrder: String) {
        GetflixApi.getflixApiService.searchProductsSort(ProSearchSortRequest(sortBy, sortOrder,subId))
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


    fun searchByFilter(query: String?,subId: Int?, vendor: Int?, rating: Double?, price: Double?, sortBy: String?, sortOrder: String?) {
        GetflixApi.getflixApiService.searchProductsBySubcategory(ProSearchBySubcategoryRequest(query,subId,vendor,rating,price,sortBy,sortOrder))
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


}