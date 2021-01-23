package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.ListModel
import com.example.getflix.models.ListsModel
import com.example.getflix.service.GetflixApi
import kotlinx.coroutines.*

class ListViewModel : ViewModel() {

    private val _listOfLists = MutableLiveData<ListsModel>()
    val listOfLists: LiveData<ListsModel>
        get() = _listOfLists

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }


    fun getCustomerLists() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getLists("Bearer " + MainActivity.StaticData.user!!.token)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _listOfLists.value = it
                        println("aaaaa")
                        println(_listOfLists.value.toString())
                    }
                }
            }
        }
    }


}