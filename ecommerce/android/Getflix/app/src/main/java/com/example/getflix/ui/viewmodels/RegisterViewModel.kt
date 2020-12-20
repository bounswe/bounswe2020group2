package com.example.getflix.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.getflix.service.requests.SignUpRequest
import com.example.getflix.service.responses.SignUpResponse
import com.example.getflix.service.GetflixApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _signUpCredentials = MutableLiveData<SignUpRequest>()
    val signUpRequest: LiveData<SignUpRequest?>
        get() = _signUpCredentials

    private val _canSignUp = MutableLiveData<SignUpResponse?>()
    val canSignUp: LiveData<SignUpResponse?>
        get() = _canSignUp

    fun setSignUpCredentials(username: String, mail: String, password: String, firstName: String, lastName: String, phoneNumber: String,conPassword: String): Boolean {
        if (username.isEmpty() or mail.isEmpty() or password.isEmpty() or firstName.isEmpty() or lastName.isEmpty() or phoneNumber.isEmpty())
            return false
        if (password!=conPassword) {
            return false
        }
        if (password.length<8 || username.length<6 || firstName.length<2 || lastName.length<2) {
            return false
        }
        _signUpCredentials.value = SignUpRequest(username, mail, password, firstName, lastName, phoneNumber)
        signUp()
        return true
    }

    private fun signUp() {
        GetflixApi.getflixApiService.signUp(_signUpCredentials.value!!)
                .enqueue(object :
                        Callback<SignUpResponse> {
                    override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                        _canSignUp.value = null
                    }

                    override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                        _canSignUp.value = response.body()
                        println(response.body().toString())
                    }
                }

                )
    }
}


