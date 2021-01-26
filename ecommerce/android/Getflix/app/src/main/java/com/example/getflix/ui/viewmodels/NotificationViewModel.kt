package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.ListModel
import com.example.getflix.models.NotificationModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.AddressAddRequest
import com.example.getflix.service.responses.AddressAddResponse
import com.example.getflix.service.responses.SeenResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel : ViewModel() {

    private val _notificationList = MutableLiveData<List<NotificationModel>>()
    val notificationList: LiveData<List<NotificationModel>>
        get() = _notificationList

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    init{
        _notificationList.value = null
    }

    fun getNotifications() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getNotifications("Bearer " + MainActivity.StaticData.user!!.token)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _notificationList.value = it
                        println(_notificationList.value?.size)
                        println(_notificationList.value.toString())
                    }
                }
            }
        }
    }

    fun readNotification(notId: Int) {
        GetflixApi.getflixApiService.readNotification(
            "Bearer " + MainActivity.StaticData.user!!.token, notId
        )
            .enqueue(object :
                Callback<SeenResponse> {
                override fun onFailure(call: Call<SeenResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<SeenResponse>,
                    response: Response<SeenResponse>
                ) {


                }
            }
            )
    }

    fun readAllNotifications() {
        GetflixApi.getflixApiService.readAllNotifications(
            "Bearer " + MainActivity.StaticData.user!!.token
        )
            .enqueue(object :
                Callback<SeenResponse> {
                override fun onFailure(call: Call<SeenResponse>, t: Throwable) {
                    println("failure")
                }

                override fun onResponse(
                    call: Call<SeenResponse>,
                    response: Response<SeenResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    println("aaa")
                    getNotifications()
                   // if (response.code() == 200)
                   //     _navigateBack.value = true
                }
            }
            )
    }
}