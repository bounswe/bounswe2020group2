package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.CardModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.responses.CardDeleteResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditCartViewModel : ViewModel() {

    private val _creditCartList = MutableLiveData<MutableList<CardModel>>()
    val creditList: LiveData<MutableList<CardModel>>
        get() = _creditCartList

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    fun deleteCustomerCard(cardId: Int) {
        GetflixApi.getflixApiService.deleteCustomerCard("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, cardId)
                .enqueue(object :
                        Callback<CardDeleteResponse> {
                    override fun onFailure(call: Call<CardDeleteResponse>, t: Throwable) {
                        println("failure")
                    }

                    override fun onResponse(
                            call: Call<CardDeleteResponse>,
                            response: Response<CardDeleteResponse>
                    ) {
                        println(response.body().toString())
                        println(response.code())
                        if (response.body()!!.status.succcesful)
                            println(response.body().toString())
                    }
                }
                )
    }

    fun getCustomerCards() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerCards("Bearer " + MainActivity.StaticData.user!!.token, MainActivity.StaticData.user!!.id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _creditCartList.value = response.body()!!.cards as MutableList<CardModel>
                    }
                }
            }
        }
    }
}