package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.user
import com.example.getflix.models.AddressListModel
import com.example.getflix.models.AddressModel
import com.example.getflix.models.CardModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.GetflixApi.getflixApiService
import com.example.getflix.service.requests.AddressAddRequest
import com.example.getflix.service.requests.AddressUpdateRequest
import com.example.getflix.service.responses.AddressAddResponse
import com.example.getflix.service.responses.AddressDeleteResponse
import com.example.getflix.service.responses.AddressUpdateResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressViewModel : ViewModel() {

    private val _addressList = MutableLiveData<MutableList<AddressModel>>()
    val addressList: LiveData<MutableList<AddressModel>>
        get() = _addressList

    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean>
        get() = _navigateBack

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }


    fun getCustomerAddresses() {
        getflixApiService.getCustomerAddresses("Bearer " + user!!.token, user!!.id)
            .enqueue(object : Callback<AddressListModel> {
                override fun onFailure(call: Call<AddressListModel>, t: Throwable) {
                    _addressList.value = mutableListOf()
                }

                override fun onResponse(
                    call: Call<AddressListModel>,
                    response: Response<AddressListModel>
                ) {
                    response.body().let {
                        if (it != null) {
                            _addressList.value = it.addresses as MutableList<AddressModel>

                        }
                        else _addressList.value = mutableListOf()
                    }
                }

            })

    }

    fun deleteCustomerAddress(addressId: Int) {
        GetflixApi.getflixApiService.deleteCustomerAddress(
            "Bearer " + user!!.token,
            user!!.id, addressId
        )
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
                    getCustomerAddresses()

                }
            }
            )
    }

    fun addCustomerAddress(addressRequest: AddressAddRequest) {
        GetflixApi.getflixApiService.addCustomerAddress(
            "Bearer " + user!!.token,
            user!!.id, addressRequest
        )
            .enqueue(object :
                Callback<AddressAddResponse> {
                override fun onFailure(call: Call<AddressAddResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<AddressAddResponse>,
                    response: Response<AddressAddResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    if (response.code() == 200)
                        _navigateBack.value = true
                }
            }
            )
    }

    fun updateCustomerAddress(addressId: Int, updateReq: AddressUpdateRequest) {
        GetflixApi.getflixApiService.updateCustomerAddress(
            "Bearer " + user!!.token,
            user!!.id, addressId, updateReq
        )
            .enqueue(object :
                Callback<AddressUpdateResponse> {
                override fun onFailure(call: Call<AddressUpdateResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<AddressUpdateResponse>,
                    response: Response<AddressUpdateResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    if (response.code() == 200)
                        _navigateBack.value = true
                }
            }
            )
    }

    fun resetNavigate() {
        _navigateBack.value = false
    }
}