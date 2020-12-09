package com.example.getflix.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.getflix.models.PModel


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

    fun navigationComplete(){
        _onCategoryClick.value = null
    }


}
