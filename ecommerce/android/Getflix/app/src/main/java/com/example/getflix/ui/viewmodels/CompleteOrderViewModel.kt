package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.user
import com.example.getflix.models.AddressListModel
import com.example.getflix.models.AddressModel
import com.example.getflix.models.CardListModel
import com.example.getflix.models.CardModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.GetflixApi.getflixApiService
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

    init {
        _addressList.value = arrayListOf()
        _creditCartList.value = mutableListOf()
        getCustomerCards()
        getCustomerAddresses()
    }

    companion object {
        var currentAddress = MutableLiveData<AddressModel?>()
        var currentCreditCard = MutableLiveData<CardModel?>()
    }

    fun getCustomerAddresses() {
        if (user != null) {
            getflixApiService.getCustomerAddresses("Bearer " + user!!.token, user!!.id)
                .enqueue(object : Callback<AddressListModel> {
                    override fun onFailure(call: Call<AddressListModel>, t: Throwable) {
                        _addressList.value = arrayListOf()
                    }

                    override fun onResponse(
                        call: Call<AddressListModel>,
                        response: Response<AddressListModel>
                    ) {
                        if (response.body() != null) {
                            val addresses = response.body()!!.addresses
                            if (addresses != null)
                                _addressList.value = addresses as ArrayList<AddressModel>?
                            else
                                _addressList.value = arrayListOf()
                            if (addresses != null && addresses.isNotEmpty())
                                currentAddress.value = response.body()?.addresses?.get(0)

                        }
                    }

                })
        }
    }

    fun getCustomerCards() {
        if (user != null) {
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
                            if (cards != null && cards.isNotEmpty()) {
                                currentCreditCard.value = response.body()?.cards?.get(0)
                            }

                        }
                    }

                })
        }
    }

    fun makePurchase(addressId: Int, cardId: Int) {
        getflixApiService.customerCheckout(
            "Bearer " + user!!.token,
            CustomerCheckoutRequest(addressId, cardId)
        )
            .enqueue(object :
                Callback<CustomerCheckoutResponse> {
                override fun onFailure(call: Call<CustomerCheckoutResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CustomerCheckoutResponse>,
                    response: Response<CustomerCheckoutResponse>
                ) {
                    _navigateHome.value = true

                }
            }
            )
    }

    fun resetNavigate() {
        _navigateHome.value = false
    }

}