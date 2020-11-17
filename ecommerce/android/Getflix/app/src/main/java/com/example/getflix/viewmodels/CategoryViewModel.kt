package com.example.getflix.ui.fragment

import android.app.Application
import androidx.lifecycle.*
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.ProductModel
import com.example.getflix.models.SubcategoryModel

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val _categories = MutableLiveData<HashMap<Int, CategoryModel>>()
    val categories: LiveData<HashMap<Int, CategoryModel>>
        get() = _categories

    private val _displayedCategory = MutableLiveData<CategoryModel>()
    val displayedCategory: LiveData<CategoryModel>
        get() = _displayedCategory

    fun setCategory(categoryId: Int) {
        var b = ProductModel(1, "jn", "km", 22.toFloat(), "skds", "lkslkd")
        var a = listOf<ProductModel>(b)
        var subcategoryModel1 = SubcategoryModel(1, 2, "Computers", a)
        var subcategoryModel2 = SubcategoryModel(2, 2, "Mobile Phones", a)
        var subcategories = listOf<SubcategoryModel>(subcategoryModel1, subcategoryModel2)
        var categoryModel = CategoryModel(1, "Electronics", subcategories)
        _displayedCategory.value = categoryModel
    }


}

