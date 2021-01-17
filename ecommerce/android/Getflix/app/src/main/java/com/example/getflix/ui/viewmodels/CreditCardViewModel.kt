package com.example.getflix.ui.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.user
import com.example.getflix.doneAlert
import com.example.getflix.models.CardListModel
import com.example.getflix.models.CardModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.GetflixApi.getflixApiService
import com.example.getflix.service.requests.CardAddRequest
import com.example.getflix.service.requests.CardUpdateRequest
import com.example.getflix.service.responses.CardAddResponse
import com.example.getflix.service.responses.CardDeleteResponse
import com.example.getflix.service.responses.CardUpdateResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditCardViewModel : ViewModel() {

    private val _creditCartList = MutableLiveData<MutableList<CardModel>>()
    val creditList: LiveData<MutableList<CardModel>>
        get() = _creditCartList

    private val _navigateOrder = MutableLiveData<Boolean>()
    val navigateOrder: LiveData<Boolean>
        get() = _navigateOrder

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    fun deleteCustomerCard(cardId: Int) {
        getflixApiService.deleteCustomerCard("Bearer " + user!!.token, user!!.id, cardId)
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
                    getCustomerCards()
                    if (response.body()!!.status.succcesful)
                        println(response.body().toString())
                }
            }
            )
    }

    fun getCustomerCards() {
        getflixApiService.getCustomerCards("Bearer " + user!!.token, user!!.id)
            .enqueue(object : Callback<CardListModel> {
                override fun onFailure(call: Call<CardListModel>, t: Throwable) {
                    _creditCartList.value = mutableListOf()
                }

                override fun onResponse(
                    call: Call<CardListModel>,
                    response: Response<CardListModel>
                ) {
                    if (response.body() != null) {
                        val cards = response.body()!!.cards
                        if (cards != null)
                            _creditCartList.value = cards as MutableList<CardModel>?
                        else
                            _creditCartList.value = arrayListOf()
                        if (cards != null && cards.isNotEmpty())
                            CompleteOrderViewModel.currentCreditCard.value =
                                response.body()?.cards?.get(0)

                    }
                }

            })
    }


    fun addCustomerCard(cardRequest: CardAddRequest) {
        getflixApiService.addCustomerCard("Bearer " + user!!.token, user!!.id, cardRequest)
            .enqueue(object :
                Callback<CardAddResponse> {
                override fun onFailure(call: Call<CardAddResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CardAddResponse>,
                    response: Response<CardAddResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    if (response.code() == 200) {
                        println(response.body().toString())
                        _navigateOrder.value = true

                        //doneAlert(fragment,"Credit card added successfully",::navigateOrder)
                    }
                }
            }
            )
    }

    fun updateCustomerCard(cardId: Int, cardRequest: CardUpdateRequest) {
        getflixApiService.updateCustomerCard(
            "Bearer " + user!!.token,
            user!!.id,
            cardId,
            cardRequest
        )
            .enqueue(object :
                Callback<CardUpdateResponse> {
                override fun onFailure(call: Call<CardUpdateResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CardUpdateResponse>,
                    response: Response<CardUpdateResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    if (response.code() == 200)
                        _navigateOrder.value = true
                }
            }
            )
    }

    fun resetNavigate() {
        _navigateOrder.value = false
    }
}