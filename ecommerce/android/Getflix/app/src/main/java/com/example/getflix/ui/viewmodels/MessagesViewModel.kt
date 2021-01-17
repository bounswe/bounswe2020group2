package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.MessageListModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.AddressAddRequest
import com.example.getflix.service.requests.SendMessageRequest
import com.example.getflix.service.responses.AddressAddResponse
import com.example.getflix.service.responses.SendMessageResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesViewModel: ViewModel() {

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

    /*fun addCustomerAddress(addressRequest: AddressAddRequest) {
        GetflixApi.getflixApiService.addCustomerAddress("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, addressRequest)
            .enqueue(object :
                Callback<AddressAddResponse> {
                override fun onFailure(call: Call<AddressAddResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<AddressAddResponse>,
                    response: Response<AddressAddResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    if (response.code()==200)
                        _navigateBack.value = true
                }
            }
            )
    } */


    fun sendMessage(messageRequest: SendMessageRequest) {
        GetflixApi.getflixApiService.sendMessage("Bearer " + MainActivity.StaticData.user!!.token,messageRequest)
            .enqueue(object :
                Callback<SendMessageResponse> {
                override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<SendMessageResponse>,
                    response: Response<SendMessageResponse>
                ) {
                    if (response.code()==200) {
                        println(response.body().toString())
                    }
                }
            }
            )
    }

}