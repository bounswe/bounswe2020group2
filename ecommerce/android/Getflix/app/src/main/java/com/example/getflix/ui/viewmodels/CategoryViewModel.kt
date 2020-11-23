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





    fun setCategory(categoryName: String, products: List<PModel>?) {
        val subList = arrayListOf<SubcategoryModel>()
        for(pro in products!!) {
            if(!subList.any {pro.subcategory == it.name}) {
                val products = arrayListOf<PModel>()
                products.add(pro)
                subList.add(SubcategoryModel(pro.subcategory, products))
            } else {
                for(sub in subList) {
                    if(sub.name==categoryName) {
                        var ind = subList.indexOf(sub)
                        subList[ind].products?.add(pro)
                    }
                }
            }
        }
        /*var zaraJacket1 = ProductModel(1, "Jacket", 2, null, "Zara" )
        var zaraJacket2 = ProductModel(2, "Jacket", 2, null, "Zara" )
        var zaraJacket3 = ProductModel(3, "Jacket", 2, null, "Zara" )
        var jackets = listOf<ProductModel>(zaraJacket1, zaraJacket2, zaraJacket3)
        var subcategoryModel2 = SubcategoryModel(1, 1, "Jackets", jackets)
        var zaraSkirt1 = ProductModel(4, "Skirt", 2, null, "Zara")
        var zaraSkirt2 = ProductModel(5, "Skirt", 2, null, "Zara")
        var zaraSkirt3 = ProductModel(6, "Skirt", 2, null, "Zara")
        var skirts = listOf<ProductModel>(zaraSkirt1, zaraSkirt2, zaraSkirt3)
        var subcategoryModel1 = SubcategoryModel(2, 1, "Skirts", skirts)
        var subcategories = listOf<SubcategoryModel>(subcategoryModel1, subcategoryModel2)
        var categoryModel = CategoryModel(1, "Woman", subcategories) */
        _displayedCategory.value = CategoryModel(categoryName, subList)
        println("burdasın")
        println(CategoryModel(categoryName, subList).toString())
    }


}

