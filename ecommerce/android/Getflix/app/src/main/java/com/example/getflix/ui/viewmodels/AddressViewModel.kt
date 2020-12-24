package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.AddressModel
import com.example.getflix.models.CardModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.responses.AddressDeleteResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressViewModel  : ViewModel() {

    private val _addressList = MutableLiveData<MutableList<AddressModel>>()
    val addressList: LiveData<MutableList<AddressModel>>
        get() = _addressList

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }


    fun getCustomerAddresses() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerAddresses("Bearer " + MainActivity.StaticData.user!!.token, MainActivity.StaticData.user!!.id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _addressList.value = response.body()!!.addresses as MutableList<AddressModel>
                    }
                }
            }
        }
    }

    fun deleteCustomerAddress(addressId: Int) {
        GetflixApi.getflixApiService.deleteCustomerAddress("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, addressId)
                .enqueue(object :
                        Callback<AddressDeleteResponse> {
                    override fun onFailure(call: Call<AddressDeleteResponse>, t: Throwable) {
                        println("failure")
                    }

                    override fun onResponse(
                            call: Call<AddressDeleteResponse>,
                            response: Response<AddressDeleteResponse>
                    ) {
                        println(response.body().toString())
                        println(response.code())
                        if (response.body()!!.status.succcesful)
                            println(response.body().toString())
                    }
                }
                )
    }
}