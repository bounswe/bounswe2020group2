package com.example.getflix.ui.viewmodels

import androidx.lifecycle.*
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.ProductModel
import com.example.getflix.models.SubcategoryModel

class CategoryViewModel() : ViewModel() {

    private val _categories = MutableLiveData<HashMap<Int, CategoryModel>>()
    val categories: LiveData<HashMap<Int, CategoryModel>>
        get() = _categories

    private val _displayedCategory = MutableLiveData<CategoryModel>()
    val displayedCategory: LiveData<CategoryModel>
        get() = _displayedCategory

    fun setCategory(id: Int) {

    }


}


