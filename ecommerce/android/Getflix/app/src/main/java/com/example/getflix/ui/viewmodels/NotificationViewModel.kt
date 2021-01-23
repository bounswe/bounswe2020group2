package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.ListModel
import com.example.getflix.models.NotificationModel
import com.example.getflix.service.GetflixApi
import kotlinx.coroutines.*

class NotificationViewModel : ViewModel() {

    private val _notificationList = MutableLiveData<List<NotificationModel>>()
    val notificationList: LiveData<List<NotificationModel>>
        get() = _notificationList

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    fun getNotifications() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getNotifications("Bearer " + MainActivity.StaticData.user!!.token)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _notificationList.value = it
                        println("aaaaa")
                        println(_notificationList.value.toString())
                    }
                }
            }
        }
    }
}