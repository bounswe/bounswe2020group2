package com.example.getflix.ui.fragment

import android.app.Application
import androidx.lifecycle.*
import com.example.getflix.model.ProductModel

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {
    private val _categories =
        MutableLiveData<HashMap<String, HashMap<String, List<ProductModel>>>>()
    val products: LiveData<HashMap<String, HashMap<String, List<ProductModel>>>>
        get() = _categories

    private val _displayedSubcategories = MutableLiveData<HashMap<String, List<ProductModel>>>()
    val displayedCategories: LiveData<HashMap<String, List<ProductModel>>>
        get() = _displayedSubcategories

    private lateinit var _displayedCategory: String

    fun setCategory(category: String) {
        _displayedCategory = category
        setSubCategories()
    }

    private fun setSubCategories() {
        _categories.value?.get(_displayedCategory)?.map { (subcategory, products) ->
            _displayedSubcategories.value?.set(subcategory, products)
        }
    }

}

