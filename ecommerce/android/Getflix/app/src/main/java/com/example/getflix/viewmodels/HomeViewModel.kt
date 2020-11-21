package com.example.getflix.viewmodels

import android.app.Application
import androidx.lifecycle.*


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _onCategoryClick = MutableLiveData<Int>()
    val onCategoryClick: LiveData<Int>
        get() = _onCategoryClick

    fun setOnCategoryClick(id: Int) {
        _onCategoryClick.value = id
    }
}
