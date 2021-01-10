package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.CardModel
import com.example.getflix.models.MessageListModel
import com.example.getflix.service.GetflixApi
import kotlinx.coroutines.*

class CustMessagesViewModel: ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    private val _messageList = MutableLiveData<MessageListModel>()
    val messageList: LiveData<MessageListModel>
        get() = _messageList

    fun getMessages() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getMessages("Bearer " + MainActivity.StaticData.user!!.token)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _messageList.value = it
                    }
                }
            }
        }
    }

}