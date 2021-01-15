package com.example.getflix.ui.viewmodels

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.auth
import com.example.getflix.service.requests.SignUpRequest
import com.example.getflix.service.responses.SignUpResponse
import com.example.getflix.service.GetflixApi
import kotlinx.android.synthetic.main.activity_main.*
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

    fun setSignUpCredentials(
        fragment: Fragment,
        username: String,
        mail: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        conPassword: String
    ): Boolean {

        _signUpCredentials.value =
            SignUpRequest(username, mail, password, firstName, lastName, phoneNumber)
        signUp()
        return true
    }
    init{
        _canSignUp.value = null
    }
    private fun signUp() {

                    // Sign in success, update UI with the signed-in user's information
                    GetflixApi.getflixApiService.signUp(_signUpCredentials.value!!)
                        .enqueue(object :
                            Callback<SignUpResponse> {
                            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                                _canSignUp.value = null
                            }

                            override fun onResponse(
                                call: Call<SignUpResponse>,
                                response: Response<SignUpResponse>
                            ) {
                                _canSignUp.value = response.body()
                                println(response.message())
                                auth.createUserWithEmailAndPassword(
                                    _signUpCredentials.value!!.email,
                                    _signUpCredentials.value!!.password
                                )
                                    .addOnCompleteListener {
                                        if (it.isSuccessful || response.body()?.message == "Username is already in use" ) {
                                            println("Çalışma")
                                        }
                                        else {
                                            println("Hata1" + response.body()?.message)
                                            _canSignUp.value = null
                                        }
                                    }

                            }
                        }

                        )
    }
}


