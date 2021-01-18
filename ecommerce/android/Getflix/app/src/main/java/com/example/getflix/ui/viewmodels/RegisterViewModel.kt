package com.example.getflix.ui.viewmodels

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.getflix.activities.MainActivity.StaticData.auth
import com.example.getflix.service.requests.SignUpRequest
import com.example.getflix.service.responses.SignUpResponse
import com.example.getflix.service.GetflixApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _signUpCredentials = MutableLiveData<SignUpRequest>()

    private val _canSignUp = MutableLiveData<SignUpResponse?>()
    val canSignUp: LiveData<SignUpResponse?>
        get() = _canSignUp

    private val _emailVerificationSent = MutableLiveData<Boolean>()
    val emailVerificationSent: LiveData<Boolean>
        get() = _emailVerificationSent

    fun setSignUpCredentials(
        fragment: Fragment,
        username: String,
        mail: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        conPassword: String,
    ): Boolean {

        _signUpCredentials.value =
            SignUpRequest(username, mail, password, firstName, lastName, phoneNumber)
        signUp()
        return true
    }

    init {
        _emailVerificationSent.value = false
        _canSignUp.value = null
    }

    private fun signUp() {

        // Sign in success, update UI with the signed-in user's information
        GetflixApi.getflixApiService.signUp(_signUpCredentials.value!!)
            .enqueue(object :
                Callback<SignUpResponse> {
                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    println("backend endpointi çalışmıyor.")
                    _canSignUp.value = null
                }

                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    _canSignUp.value = response.body()
                    println("Şu an backend endpointi doğru girdi.")
                    println(response.body())
                    if (response.body() != null && response.body()!!.successful) {
                        auth.createUserWithEmailAndPassword(
                            _signUpCredentials.value!!.email,
                            _signUpCredentials.value!!.password
                        )
                            .addOnCompleteListener {
                                println("Şu an fire base register'a girdi")
                                val firebaseUser = auth.currentUser
                                if (firebaseUser?.isEmailVerified == false) {
                                    firebaseUser.let {
                                        it.sendEmailVerification().addOnSuccessListener {
                                            println("Şu an fire base register maili attı")
                                            _emailVerificationSent.value = true
                                        }.addOnFailureListener {
                                            println("Şu an firebase register maili atamadı")
                                            _emailVerificationSent.value = false
                                        }
                                    }
                                }

                                if (it.isSuccessful || response.body()?.message == "Username is already in use") {
                                    println("Aynı  kullanıcı var veya başarılı kısmına girdi on responseda")

                                } else {
                                    println("Hata kısmındayız şu an on responnseun hata:" + response.body()?.message)
                                    _canSignUp.value = null
                                }
                            }.addOnFailureListener {
                                println("Fire base mail atamama hatası :" + it.message)
                                _canSignUp.value = null
                            }


                    }
                }
            })
    }


    fun onEmailVerificationMailComplete() {
        _emailVerificationSent.value = null
    }
}



