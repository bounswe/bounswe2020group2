package com.example.getflix.ui.viewmodels


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.Status
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.VendorOrderStatusRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorOrderViewModel :ViewModel() {
    private val _onStatusChanged = MutableLiveData<Boolean?>()
    val onStatusChanged: LiveData<Boolean?>
        get() = _onStatusChanged
    init {
        _onStatusChanged.value = null
    }
    fun changeStatusOfOrder(orderId: Int, orderStatus: String) {
        val request = VendorOrderStatusRequest(orderId, orderStatus)
        GetflixApi.getflixApiService.updateOrderStatus(
            "Bearer " + MainActivity.StaticData.user!!.token, request
        )
            .enqueue(object :
                Callback<Status> {
                override fun onFailure(call: Call<Status>, t: Throwable) {
                    _onStatusChanged.value = false
                }

                override fun onResponse(
                    call: Call<Status>,
                    response: Response<Status>
                ) {
                    _onStatusChanged.value = true
                }
            }
            )
    }
    fun onStatusChangeCompleted(){
        _onStatusChanged.value = null
    }
}