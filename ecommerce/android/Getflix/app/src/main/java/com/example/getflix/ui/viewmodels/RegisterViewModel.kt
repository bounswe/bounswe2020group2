package com.example.getflix.ui.viewmodels

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.auth
import com.example.getflix.service.requests.SignUpRequest
import com.example.getflix.service.responses.SignUpResponse
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.AddressAddRequest
import com.example.getflix.service.requests.VendorSignupRequest
import com.example.getflix.service.responses.AddressAddResponse
import com.example.getflix.service.responses.VendorSignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _signUpCredentials = MutableLiveData<SignUpRequest>()

    private val _canSignUp = MutableLiveData<SignUpResponse?>()
    val canSignUp: LiveData<SignUpResponse?>
        get() = _canSignUp

    private val _canSignUpVendor = MutableLiveData<VendorSignupResponse?>()
    val canSignUpVendor: LiveData<VendorSignupResponse?>
        get() = _canSignUpVendor

    val isFirebaseUser = MutableLiveData<Boolean>()

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
        _canSignUp.value = null
        isFirebaseUser.value = false
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

                    if ((response.body() != null && response.body()!!.successful && response.body()!!.message.equals("Username is already in use")).not()) {
                        isFirebaseUser.value = true
                        auth.createUserWithEmailAndPassword(
                            _signUpCredentials.value!!.email,
                            _signUpCredentials.value!!.password
                        )
                            .addOnCompleteListener {
                                isFirebaseUser.value = true
                                _canSignUp.value = response.body()
                            }.addOnFailureListener {
                                isFirebaseUser.value = false
                                _canSignUp.value = null
                            }
                    }
                }
            })
    }

    fun vendorRegister(request: VendorSignupRequest) {
        GetflixApi.getflixApiService.vendorSignup(request)
            .enqueue(object :
                Callback<VendorSignupResponse> {
                override fun onFailure(call: Call<VendorSignupResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<VendorSignupResponse>,
                    response: Response<VendorSignupResponse>
                ) {
                    if (response.code() == 200)
                       _canSignUpVendor.value = response.body()
                }
            }
            )
    }

}



