package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.CartProductListModel
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

    private val _cardProducts = MutableLiveData<MutableList<CartProductModel>?>()
    val cardProducts: LiveData<MutableList<CartProductModel>?>
        get() = _cardProducts

    private val _cardPrices = MutableLiveData<CustomerCartPriceModel>()
    val cardPrices: LiveData<CustomerCartPriceModel>
        get() = _cardPrices


    init {
        _cardPrices.value = CustomerCartPriceModel(0.0, 0.0, 0.0, 0.0)
        _cardProducts.value = null
    }

    fun getCustomerCartPrice() {
        if (MainActivity.StaticData.user != null) {
            GetflixApi.getflixApiService.getCustomerCartPrice("Bearer " + MainActivity.StaticData.user!!.token)
                .enqueue(
                    object : Callback<CustomerCartPriceModel?> {
                        override fun onFailure(call: Call<CustomerCartPriceModel?>, t: Throwable) {
                            _cardPrices.value = CustomerCartPriceModel(0.0, 0.0, 0.0, 0.0)
                        }

                        override fun onResponse(
                            call: Call<CustomerCartPriceModel?>,
                            response: Response<CustomerCartPriceModel?>
                        ) {
                            if (response.body() != null) {
                                _cardPrices.value = response.body()
                            } else {
                                _cardPrices.value = CustomerCartPriceModel(0.0, 0.0, 0.0, 0.0)
                            }
                        }

                    }
                )
        }
    }

    fun getCustomerCartProducts() {
        if (MainActivity.StaticData.user != null) {
            GetflixApi.getflixApiService.getCustomerAllCartProducts(
                "Bearer " + MainActivity.StaticData.user!!.token,
                MainActivity.StaticData.user!!.id
            )
                .enqueue(object :
                    Callback<CartProductListModel?> {
                    override fun onFailure(call: Call<CartProductListModel?>, t: Throwable) {
                        _cardProducts.value = null
                    }

                    override fun onResponse(
                        call: Call<CartProductListModel?>,
                        response: Response<CartProductListModel?>
                    ) {
                        response.body().let {
                            _cardProducts.value = it?.cartProducts as MutableList<CartProductModel>?
                        }

                    }
                }
                )
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
        GetflixApi.getflixApiService.deleteCustomerCartProduct(
            "Bearer " + MainActivity.StaticData.user!!.token,
            MainActivity.StaticData.user!!.id,
            scId
        )
            .enqueue(object :
                Callback<CardProDeleteResponse> {
                override fun onFailure(call: Call<CardProDeleteResponse>, t: Throwable) {
                    println("failure")
                }

                override fun onResponse(
                    call: Call<CardProDeleteResponse>,
                    response: Response<CardProDeleteResponse>
                ) {
                    getCustomerCartProducts()
                    getCustomerCartPrice()
                }
            }
            )
    }


}

