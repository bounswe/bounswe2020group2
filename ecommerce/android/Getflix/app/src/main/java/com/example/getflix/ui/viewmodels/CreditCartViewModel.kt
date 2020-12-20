package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.CardModel

class CreditCartViewModel : ViewModel() {

    private val _creditCartList = MutableLiveData<MutableList<CardModel>>()
    val creditList: LiveData<MutableList<CardModel>>
        get() = _creditCartList

    fun addCreditCart(creditCartModel: CardModel) {
        if (_creditCartList.value != null) {
            val creditCards = _creditCartList.value
            creditCards?.add(creditCartModel)
            _creditCartList.value = creditCards
        } else {
            val creditCards = arrayListOf<CardModel>()
            creditCards.add(creditCartModel)
            _creditCartList.value = creditCards
        }
    }
}