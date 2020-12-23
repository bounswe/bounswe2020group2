package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.CardModel
import com.example.getflix.service.GetflixApi
import kotlinx.coroutines.*

class CreditCartViewModel : ViewModel() {

    private val _creditCartList = MutableLiveData<MutableList<CardModel>>()
    val creditList: LiveData<MutableList<CardModel>>
        get() = _creditCartList

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
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