package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.MessageListModel
import com.example.getflix.service.BASE_URL
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.GetflixApiService
import com.example.getflix.service.requests.SendMessageRequest
import com.example.getflix.service.responses.SendMessageResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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


    fun sendMessage(messageRequest: SendMessageRequest) {
        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .build()
        retrofit.create(GetflixApiService::class.java).sendMessage("Bearer " + MainActivity.StaticData.user!!.token,messageRequest)
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