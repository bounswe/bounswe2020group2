package com.example.getflix.ui.viewmodels

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.infoAlert
import com.example.getflix.models.OrderModel
import com.example.getflix.models.Status
import com.example.getflix.models.VendorOrderModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.VendorOrderStatusRequest
import com.example.getflix.service.responses.LoginResponse
import com.example.getflix.service.responses.VendorOrderResponse
import com.example.getflix.service.responses.VendorProUpdateResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorOrdersViewModel : ViewModel() {

    private val _orders = MutableLiveData<MutableList<VendorOrderModel>>()
    val orders: LiveData<MutableList<VendorOrderModel>>
        get() = _orders

    private val _onLogin = MutableLiveData<Boolean>()
    val onLogin: LiveData<Boolean>
        get() = _onLogin

    init {
        getOrders()
    }

    private fun getOrders() {
        GetflixApi.getflixApiService.getVendorOrders("Bearer " + MainActivity.StaticData.user!!.token)
            .enqueue(object :
                Callback<VendorOrderResponse> {
                override fun onFailure(call: Call<VendorOrderResponse>, t: Throwable) {
                    _orders.value = mutableListOf()
                }

                override fun onResponse(
                    call: Call<VendorOrderResponse>,
                    response: Response<VendorOrderResponse>
                ) {
                    if (response.body() != null) {
                        val orders = response.body()!!.orders
                        _orders.value = orders as MutableList<VendorOrderModel>
                    } else {
                        _orders.value = mutableListOf()
                    }

                }
            }
            )
    }

    fun changeStatusOfOrder(orderId: Int, orderStatus: String, context: Context) {
        val request = VendorOrderStatusRequest(orderId, orderStatus)
        GetflixApi.getflixApiService.updateOrderStatus(
            "Bearer " + MainActivity.StaticData.user!!.token, request
        )
            .enqueue(object :
                Callback<Status> {
                override fun onFailure(call: Call<Status>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Oops! We could not change the status of your order...",
                        Toast.LENGTH_SHORT
                    )
                }

                override fun onResponse(
                    call: Call<Status>,
                    response: Response<Status>
                ) {
                    getOrders()
                }
            }
            )
    }

}