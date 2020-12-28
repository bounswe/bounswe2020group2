package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.CartProductModel
import com.example.getflix.models.CustomerCartPriceModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.CardProUpdateRequest
import com.example.getflix.service.responses.CardProDeleteResponse
import com.example.getflix.service.responses.CardProUpdateResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {

    private val _cardProducts = MutableLiveData<MutableList<CartProductModel>>()
    val cardProducts: LiveData<MutableList<CartProductModel>>
        get() = _cardProducts

    private val _cardPrices = MutableLiveData<CustomerCartPriceModel>()
    val cardPrices: LiveData<CustomerCartPriceModel>
        get() = _cardPrices

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }
    val onSubmit = MutableLiveData<Boolean>()
    init {
        onSubmit.value = false
    }

    fun getCustomerCartPrice() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response =
                GetflixApi.getflixApiService.getCustomerCartPrice("Bearer " + MainActivity.StaticData.user!!.token)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _cardPrices.value = it
                    }
                    if(onSubmit.value==false){
                        onSubmit.value = true
                    }
                }
            }
        }
    }

    fun getCustomerCartProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerAllCartProducts(
                "Bearer " + MainActivity.StaticData.user!!.token,
                MainActivity.StaticData.user!!.id
            )
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    println("succesfull mu")
                    response.body().let { it ->
                        _cardProducts.value =
                            response.body()!!.cartProducts as MutableList<CartProductModel>
                        println(_cardProducts.value.toString())
                    }
                    if(onSubmit.value==false){
                        onSubmit.value = true
                    }
                }
            }
        }
    }

    fun updateCustomerCartProduct(amount: Int, scId: Int, proId: Int) {
        GetflixApi.getflixApiService.updateCustomerCartProduct(
            "Bearer " + MainActivity.StaticData.user!!.token,
            MainActivity.StaticData.user!!.id,
            scId,
            CardProUpdateRequest(proId, amount)
        )
            .enqueue(object :
                Callback<CardProUpdateResponse> {
                override fun onFailure(call: Call<CardProUpdateResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CardProUpdateResponse>,
                    response: Response<CardProUpdateResponse>
                ) {
                    getCustomerCartProducts()
                    getCustomerCartPrice()
                }
            }
            )
    }

    fun deleteCustomerCartProduct(scId: Int) {
        GetflixApi.getflixApiService.deleteCustomerCartProduct("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, scId)
            .enqueue(object :
                Callback<CardProDeleteResponse> {
                override fun onFailure(call: Call<CardProDeleteResponse>, t: Throwable) {
                    println("failure")
                }

                override fun onResponse(
                    call: Call<CardProDeleteResponse>,
                    response: Response<CardProDeleteResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    getCustomerCartProducts()
                    getCustomerCartPrice()
                }
            }
            )
    }


}