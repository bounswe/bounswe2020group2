package com.example.getflix.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.getflix.models.ProductModel
import com.example.getflix.service.GetflixApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _onCategoryClick = MutableLiveData<Int>()
    val onCategoryClick: LiveData<Int>
        get() = _onCategoryClick

    private val _todaysDeals = MutableLiveData<List<ProductModel>>()
    val todaysDeals: LiveData<List<ProductModel>>?
        get() = _todaysDeals

    private val _trendingProducts = MutableLiveData<List<ProductModel>>()
    val trendingProducts: LiveData<List<ProductModel>>?
        get() = _trendingProducts

    private val _recommendedProducts = MutableLiveData<List<ProductModel>>()
    val recommendedProducts: LiveData<List<ProductModel>>?
        get() = _recommendedProducts

    private val _editorPicks = MutableLiveData<List<ProductModel>>()
    val editorPicks: LiveData<List<ProductModel>>?
        get() = _editorPicks


    fun setOnCategoryClick(id: Int) {
        _onCategoryClick.value = id
    }

    fun navigationComplete() {
        _onCategoryClick.value = null
    }

    init {
        getHomeProducts(12)
    }

    fun getHomeProducts(numberOfProducts: Int) {
        GetflixApi.getflixApiService.getProducts(numberOfProducts)
            .enqueue(object :
                Callback<List<ProductModel>> {
                override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                    _recommendedProducts.value = null
                    _trendingProducts.value = null
                    _todaysDeals.value = null
                    _editorPicks.value = null
                }

                override fun onResponse(
                    call: Call<List<ProductModel>>,
                    response: Response<List<ProductModel>>
                ) {
                    val rangeLength = numberOfProducts / 3
                    _editorPicks.value = response.body()
                    _recommendedProducts.value =  response.body()?.subList(2 * rangeLength, 3 * rangeLength)
                    _trendingProducts.value = response.body()?.subList(0, rangeLength)
                    _todaysDeals.value = response.body()?.subList(rangeLength, 2 * rangeLength)
                }
            }
            )
    }

}
