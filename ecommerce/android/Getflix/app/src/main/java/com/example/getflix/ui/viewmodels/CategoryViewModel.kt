package com.example.getflix.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.PModel
import com.example.getflix.models.ProductModel
import com.example.getflix.models.SubcategoryModel
import com.example.getflix.services.ProductsAPI
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val _categories = MutableLiveData<HashMap<Int, CategoryModel>>()
    val categories: LiveData<HashMap<Int, CategoryModel>>
        get() = _categories

    private val _displayedCategory = MutableLiveData<CategoryModel>()
    val displayedCategory: LiveData<CategoryModel>
        get() = _displayedCategory
    
    fun setCategory(id: Int?) {
        if (id == 1) {
            var zaraJacket1 = ProductModel(1, "Jacket", 222, null, "Zara")
            var zaraJacket2 = ProductModel(2, "Jacket", 231, null, "Zara")
            var zaraJacket3 = ProductModel(3, "Jacket", 321, null, "Zara")
            var jackets = mutableListOf<ProductModel>(zaraJacket1, zaraJacket2, zaraJacket3)
            var subcategoryModel2 = SubcategoryModel("Jackets", mutableListOf(zaraJacket1, zaraJacket2, zaraJacket3))
            var zaraSkirt1 = ProductModel(4, "Skirt", 79, null, "Zara")
            var zaraSkirt2 = ProductModel(5, "Skirt", 93, null, "Zara")
            var zaraSkirt3 = ProductModel(6, "Skirt", 102, null, "Zara")
            var skirts = mutableListOf<ProductModel>(zaraSkirt1, zaraSkirt2, zaraSkirt3)
            var zaraDress1 = ProductModel(7, "Dress", 60, null, "Zara")
            var zaraDress2 = ProductModel(8, "Dress", 142, null, "Zara")
            var zaraDress3 = ProductModel(9, "Dress", 201, null, "Zara")
            var dresses = mutableListOf<ProductModel>(zaraSkirt1, zaraSkirt2, zaraSkirt3)
            var subcategoryModel1 = SubcategoryModel("Skirts", skirts)
            var subcategoryModel3 = SubcategoryModel("Dresses", dresses)
            var subcategories = mutableListOf<SubcategoryModel>(subcategoryModel1, subcategoryModel3, subcategoryModel2)
            var categoryModel = CategoryModel("Woman", subcategories)
        } else if (id == 2) {
            var zaraJacket1 = ProductModel(10, "Jacket", 222, null, "Zara")
            var zaraJacket2 = ProductModel(11, "Jacket", 231, null, "Zara")
            var zaraJacket3 = ProductModel(12, "Jacket", 321, null, "Zara")
            var jackets = mutableListOf<ProductModel>(zaraJacket1, zaraJacket2, zaraJacket3)
            var subcategoryModel2 = SubcategoryModel("Jeans", mutableListOf(zaraJacket1, zaraJacket2, zaraJacket3))
            var zaraJean1 = ProductModel(13, "Jean", 79, null, "Zara")
            var zaraJean2 = ProductModel(14, "Jean", 93, null, "Zara")
            var zaraJean3 = ProductModel(15, "Jean", 102, null, "Zara")
            var jeans = mutableListOf<ProductModel>(zaraJean1, zaraJean2, zaraJean3)
            var zaraShirt1 = ProductModel(16, "Shirt", 60, null, "Zara")
            var zaraShirt2 = ProductModel(17, "Shirt", 142, null, "Zara")
            var zaraShirt3 = ProductModel(18, "Shirt", 201, null, "Zara")
            var shirts = mutableListOf<ProductModel>(zaraShirt1, zaraShirt2, zaraShirt3)
            var subcategoryModel1 = SubcategoryModel("Shirts", shirts)
            var subcategoryModel3 = SubcategoryModel("jeans", jeans)
            var subcategories = mutableListOf<SubcategoryModel>(subcategoryModel2, subcategoryModel3, subcategoryModel1)
            var categoryModel = CategoryModel("Man", subcategories)
        } else {

        }

    }




    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    /* fun addProductstoSubCat(product: PModel) {
        if(_subs.value!=null) {
            val subs = _subs.value
            subs?.add(subModel)
            _subs.value = subs
        } else {
            val subs = arrayListOf<SubcategoryModel>()
            subs.add(subModel)
            _subs.value = subs
        }

    }  */







}

