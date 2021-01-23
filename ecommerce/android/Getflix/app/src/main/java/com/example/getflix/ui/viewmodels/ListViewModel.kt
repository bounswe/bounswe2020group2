package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.ListModel
import com.example.getflix.models.ListsModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.AddressAddRequest
import com.example.getflix.service.requests.CreateListRequest
import com.example.getflix.service.responses.AddressAddResponse
import com.example.getflix.service.responses.CreateListResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun createList(createListRequest: CreateListRequest) {
        GetflixApi.getflixApiService.createList(
            "Bearer " + MainActivity.StaticData.user!!.token, createListRequest
        )
            .enqueue(object :
                Callback<CreateListResponse> {
                override fun onFailure(call: Call<CreateListResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CreateListResponse>,
                    response: Response<CreateListResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    if (response.code() == 200)
                      getCustomerLists()
                }
            }
            )
    }


}