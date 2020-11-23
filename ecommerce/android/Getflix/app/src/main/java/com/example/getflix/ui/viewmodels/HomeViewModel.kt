package com.example.getflix.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.PModel
import com.example.getflix.models.ProductModel
import com.example.getflix.services.ProductsAPI
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _onCategoryClick = MutableLiveData<Int>()
    val onCategoryClick: LiveData<Int>
        get() = _onCategoryClick

    private val _products = MutableLiveData<List<PModel>>()
    val products: LiveData<List<PModel>>?
        get() = _products

    fun setOnCategoryClick(id: Int) {
        _onCategoryClick.value = id
    }

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    fun getProducts() {
        val retrofit = Retrofit.Builder().baseUrl(MainActivity.StaticData.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsAPI::class.java)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getProducts()
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _products.value = it
                    }
                }
            }
        }
    }



}
