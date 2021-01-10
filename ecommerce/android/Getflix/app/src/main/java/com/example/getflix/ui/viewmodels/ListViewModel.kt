package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.ListModel

class ListViewModel : ViewModel() {

    private val _listList = MutableLiveData<MutableList<ListModel>>()
    val listList: LiveData<MutableList<ListModel>>
        get() = _listList

    fun addList(listModel: ListModel) {
        if (_listList.value != null) {
            val lists = _listList.value
            lists?.add(listModel)
            _listList.value = lists
        } else {
            val lists = arrayListOf<ListModel>()
            lists.add(listModel)
            _listList.value = lists
        }

    }
}