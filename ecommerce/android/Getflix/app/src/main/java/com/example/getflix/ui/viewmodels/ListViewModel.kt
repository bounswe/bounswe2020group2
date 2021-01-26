package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.isCustomer
import com.example.getflix.models.ListModel
import com.example.getflix.models.ListsModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.AddressAddRequest
import com.example.getflix.service.requests.CreateListRequest
import com.example.getflix.service.responses.*
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
        if(isCustomer){
            job = CoroutineScope(Dispatchers.IO).launch {
                val response = GetflixApi.getflixApiService.getLists("Bearer " + MainActivity.StaticData.user!!.token)
                withContext(Dispatchers.Main + exceptionHandler) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _listOfLists.value = it
                        }
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
                    if (response.code() == 200)
                      getCustomerLists()
                }
            }
            )
    }

    fun addProductToList(listId: Int, productId: Int) {
        GetflixApi.getflixApiService.addProductToList("Bearer " + MainActivity.StaticData.user!!.token, listId, productId)
            .enqueue(object :
                Callback<AddProductToListResponse> {
                override fun onFailure(call: Call<AddProductToListResponse>, t: Throwable) {
                    println("failure")
                }

                override fun onResponse(
                    call: Call<AddProductToListResponse>,
                    response: Response<AddProductToListResponse>
                ) {
                    if (response.body()!!.status.successful)
                        println(response.body().toString())
                }
            }
            )
    }

    fun deleteList(listId: Int) {
        GetflixApi.getflixApiService.deleteList(
            "Bearer " + MainActivity.StaticData.user!!.token, listId
        )
            .enqueue(object :
                Callback<ListDeleteResponse> {
                override fun onFailure(call: Call<ListDeleteResponse>, t: Throwable) {
                    println("failure")
                }

                override fun onResponse(
                    call: Call<ListDeleteResponse>,
                    response: Response<ListDeleteResponse>
                ) {
                    getCustomerLists()

                }
            }
            )
    }

    fun deleteProductInList(listId: Int, proId: Int) {
        GetflixApi.getflixApiService.deleteProductInList(
            "Bearer " + MainActivity.StaticData.user!!.token, listId, proId
        )
            .enqueue(object :
                Callback<DeleteProductInListResponse> {
                override fun onFailure(call: Call<DeleteProductInListResponse>, t: Throwable) {
                    println("failure")
                }

                override fun onResponse(
                    call: Call<DeleteProductInListResponse>,
                    response: Response<DeleteProductInListResponse>
                ) {
                    getCustomerLists()

                }
            }
            )
    }


}