package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.*
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.CardProRequest
import com.example.getflix.service.responses.CardProResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel : ViewModel() {

    private val _categoriesList = MutableLiveData<MutableList<CategoryModel>>()
    val categoriesList: LiveData<MutableList<CategoryModel>>
        get() = _categoriesList

    private val _products = MutableLiveData<List<ProductModel>>()
    val products: LiveData<List<ProductModel>>?
        get() = _products


    var categories = arrayListOf<CategoryModel>()

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    fun getProducts(num: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getProducts(num)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _products.value = it
                        println(_products.value.toString())
                    }
                }
            }
        }
    }

    fun getProduct(num: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getProduct(num)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _products.value = it
                        println(_products.value.toString())
                    }
                }
            }
        }
    }

    fun getUserCartProducts(id: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.userCartProducts(id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _products.value = it
                        println(_products.value.toString())
                    }
                }
            }
        }
    }

    fun addToCart(amount: Int, proId: Int) {
        GetflixApi.getflixApiService.addCartProduct(20, CardProRequest(amount, proId))
                .enqueue(object :
                        Callback<CardProResponse> {
                    override fun onFailure(call: Call<CardProResponse>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<CardProResponse>,
                        response: Response<CardProResponse>
                    ) {
                        println(response.body().toString())
                        println(response.code())
                        if (response.body()!!.successful)
                            println(response.body().toString())
                    }
                }
                )
    }

    fun addCategory(categoryModel: CategoryModel) {
        if (_categoriesList.value != null) {
            val categories = _categoriesList.value
            categories?.add(categoryModel)
            _categoriesList.value = categories
        } else {
            val categories = arrayListOf<CategoryModel>()
            categories.add(categoryModel)
            _categoriesList.value = categories
        }

    }

    fun setCategories(products: MutableList<ProductModel>) {
        val catList = arrayListOf<CategoryModel>()
        for (pro in products) {
            if (!catList.any { pro.category.name == it.name }) {
                val subCats = arrayListOf<SubcategoryModel>()
                val products = arrayListOf<ProductModel>()
                products.add(pro)
               // subCats.add(SubcategoryModel(pro.subcategory, products))
               // catList.add(CategoryModel(pro.category, subCats))
            } else {
                for (cat in catList) {
                    if (cat.name == pro.category.name) {
                        var ind = catList.indexOf(cat)
                        if (!catList[ind].subcategories!!.any { pro.subcategory.name == it.name }) {
                            val products = arrayListOf<ProductModel>()
                            products.add(pro)
                           // catList[ind].subCats.add(SubcategoryModel(pro.subcategory, products))
                        }
                    }
                }
            }
        }
        _categoriesList.value = catList
    }

}