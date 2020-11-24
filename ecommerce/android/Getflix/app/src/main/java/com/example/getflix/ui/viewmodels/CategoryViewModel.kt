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

    fun setCategory(id: Int) {
        if (id == 1) {
            var zaraJacket1 =
                PModel(1, "Jacket", 222, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraJacket2 =
                PModel(2, "Jacket", 231, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraJacket3 =
                PModel(3, "Jacket", 321, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var jackets = mutableListOf<PModel>(zaraJacket1, zaraJacket2, zaraJacket3)
            var subcategoryModel2 =
                SubcategoryModel("Jackets", mutableListOf(zaraJacket1, zaraJacket2, zaraJacket3))
            var zaraSkirt1 = PModel(4, "Skirt", 79, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraSkirt2 = PModel(5, "Skirt", 93, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraSkirt3 = PModel(6, "Skirt", 102, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var skirts = mutableListOf<PModel>(zaraSkirt1, zaraSkirt2, zaraSkirt3)
            var zaraDress1 = PModel(7, "Dress", 60, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraDress2 = PModel(8, "Dress", 142, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraDress3 = PModel(9, "Dress", 201, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var dresses = mutableListOf<PModel>(zaraSkirt1, zaraSkirt2, zaraSkirt3)
            var subcategoryModel1 = SubcategoryModel("Skirts", skirts)
            var subcategoryModel3 = SubcategoryModel("Dresses", dresses)
            var subcategories = mutableListOf<SubcategoryModel>(
                subcategoryModel1,
                subcategoryModel3,
                subcategoryModel2
            )
            var categoryModel = CategoryModel("Woman", subcategories)
        } else if (id == 2) {
            var zaraJacket1 =
                PModel(10, "Jacket", 222, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraJacket2 =
                PModel(11, "Jacket", 231, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraJacket3 =
                PModel(12, "Jacket", 321, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var jackets = mutableListOf<PModel>(zaraJacket1, zaraJacket2, zaraJacket3)
            var subcategoryModel2 =
                SubcategoryModel("Jeans", mutableListOf(zaraJacket1, zaraJacket2, zaraJacket3))
            var zaraJean1 = PModel(13, "Jean", 79, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraJean2 = PModel(14, "Jean", 93, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraJean3 = PModel(15, "Jean", 102, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var jeans = mutableListOf<PModel>(zaraJean1, zaraJean2, zaraJean3)
            var zaraShirt1 = PModel(16, "Shirt", 60, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraShirt2 = PModel(17, "Shirt", 142, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraShirt3 = PModel(18, "Shirt", 201, "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var shirts = mutableListOf<PModel>(zaraShirt1, zaraShirt2, zaraShirt3)
            var subcategoryModel1 = SubcategoryModel("Shirts", shirts)
            var subcategoryModel3 = SubcategoryModel("jeans", jeans)
            var subcategories = mutableListOf<SubcategoryModel>(
                subcategoryModel2,
                subcategoryModel3,
                subcategoryModel1
            )
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

