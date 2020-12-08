package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.PModel
import com.example.getflix.models.SubcategoryModel
import com.example.getflix.services.GetflixApi
import kotlinx.coroutines.*

class CategoriesViewModel: ViewModel() {

    private val _categoriesList = MutableLiveData<MutableList<CategoryModel>>()
    val categoriesList: LiveData<MutableList<CategoryModel>>
        get() = _categoriesList

    private val _products = MutableLiveData<List<PModel>>()
    val products: LiveData<List<PModel>>?
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

    fun addCategory(categoryModel: CategoryModel) {
        if(_categoriesList.value!=null) {
            val categories = _categoriesList.value
            categories?.add(categoryModel)
            _categoriesList.value = categories
        } else {
            val categories = arrayListOf<CategoryModel>()
            categories.add(categoryModel)
            _categoriesList.value = categories
        }

    }

    fun setCategories(products: MutableList<PModel>) {
        val catList = arrayListOf<CategoryModel>()
        for(pro in products) {
            if(!catList.any {pro.category == it.name}) {
                println("burda  " + pro.category)
                val subCats = arrayListOf<SubcategoryModel>()
                val products = arrayListOf<PModel>()
                products.add(pro)
                subCats.add(SubcategoryModel(pro.subcategory,products))
                catList.add(CategoryModel(pro.category,subCats))
            } else {
                for(cat in catList) {
                    if(cat.name==pro.category) {
                        println("burda xx  " + pro.category)
                        var ind = catList.indexOf(cat)
                        if(!catList[ind].subCats.any {pro.subcategory == it.name}) {
                            println("burda xx  " + pro.subcategory)
                            val products = arrayListOf<PModel>()
                            products.add(pro)
                            catList[ind].subCats.add(SubcategoryModel(pro.subcategory,products))
                        }
                    }
                }
            }
        }
        _categoriesList.value = catList
    }

}