package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.AddressModel

class AddressViewModel  : ViewModel() {

    private val _addressList = MutableLiveData<MutableList<AddressModel>>()
    val productList: LiveData<MutableList<AddressModel>>
        get() = _addressList

    fun addProduct(addressModel: AddressModel) {
        if (_addressList.value != null) {
            val addresses = _addressList.value
            addresses?.add(addressModel)
            _addressList.value = addresses
        } else {
            val addresses = arrayListOf<AddressModel>()
            addresses.add(addressModel)
            _addressList.value = addresses
        }
    }
}