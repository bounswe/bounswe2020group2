package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MailVerificationViewModel : ViewModel() {


    private val _onMailVerified = MutableLiveData<Boolean>()
    val onMailVerified: LiveData<Boolean>
        get() = _onMailVerified

    init {
        _onMailVerified.value = false
    }

    fun resendMail() {

        val firebaseUser = auth.currentUser
        val userId = firebaseUser?.uid
        firebaseUser!!.sendEmailVerification().addOnSuccessListener {
            println("Mail verification  view model maili attı ")
        }.addOnFailureListener {
            println(it.message)
            println("Mail verification  view model maili atamadı ")
        }
    }

/*
   fun checkIsMailVerified() {
       var firebaseUser = auth.currentUser
       var isMailVerified = firebaseUser?.isEmailVerified
       while (isMailVerified!!.not()) {
               firebaseUser?.reload()
                isMailVerified = firebaseUser?.isEmailVerified
            println("Hala buradayız")
       }
       _onMailVerified.value = isMailVerified
   }*/
}