package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.AddressModel
import com.example.getflix.models.CardModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.CustomerCheckoutRequest
import com.example.getflix.service.responses.CustomerCheckoutResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompleteOrderViewModel : ViewModel() {

    private val _addressList = MutableLiveData<MutableList<AddressModel>?>()
    val addressList: LiveData<MutableList<AddressModel>?>
        get() = _addressList


    private val _creditCartList = MutableLiveData<MutableList<CardModel>?>()
    val creditList: LiveData<MutableList<CardModel>?>
        get() = _creditCartList

    private val _navigateHome = MutableLiveData<Boolean>()
    val navigateHome: LiveData<Boolean>
        get() = _navigateHome

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }
    init{
        getCustomerCards()
        getCustomerAddresses()
    }
    companion object{
        var currentAddress =  MutableLiveData<AddressModel?>()
        var currentCreditCard =  MutableLiveData<CardModel?>()
    }

    fun getCustomerAddresses() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerAddresses("Bearer " + MainActivity.StaticData.user!!.token, MainActivity.StaticData.user!!.id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _addressList.value = response.body()!!.addresses as MutableList<AddressModel>?
                        currentAddress.value = response.body()?.addresses?.get(0)
                    }
                }else{
                    _addressList.value = null
                }
            }
        }
    }

    fun getCustomerCards() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerCards("Bearer " + MainActivity.StaticData.user!!.token, MainActivity.StaticData.user!!.id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {

                        _creditCartList.value = response.body()?.cards as MutableList<CardModel>?
                        currentCreditCard.value = response.body()?.cards?.get(0)

                }else{
                    _creditCartList.value = null
                }
            }
        }
    }

    fun makePurchase(addressId: Int, cardId: Int) {
        GetflixApi.getflixApiService.customerCheckout("Bearer " + MainActivity.StaticData.user!!.token, CustomerCheckoutRequest(addressId, cardId))
            .enqueue(object :
                Callback<CustomerCheckoutResponse> {
                override fun onFailure(call: Call<CustomerCheckoutResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CustomerCheckoutResponse>,
                    response: Response<CustomerCheckoutResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    _navigateHome.value = true

                }
            }
            )
    }

    fun resetNavigate() {
        _navigateHome.value = false
    }

    fun getCustomerCartPrice() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerCartPrice("Bearer " + MainActivity.StaticData.user!!.token)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        println("burda mısınn")
                        println(it.toString())
                    }
                }
            }
        }
    }
}