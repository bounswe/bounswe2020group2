package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.models.*
import com.example.getflix.services.GetflixApi
import com.example.getflix.models.LoginRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    private val _logReq = MutableLiveData<LoginRequest?>()
    val loginRequest: LiveData<LoginRequest?>
        get() = _logReq

    private val _onLogin = MutableLiveData<Boolean>()
    val onLogin: LiveData<Boolean>
        get() = _onLogin

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    private fun getUserFromBackend() {
        GetflixApi.getflixApiService.getUserInformation(_logReq.value!!)
                .enqueue(object :
                        Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        _logReq.value = null
                        _onLogin.value = false
                    }

                    override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                    ) {
                        if(response.body()!!.status.message == "Giriş başarılı") {
                            _onLogin.value = true
                            _user.value = response.body()!!.user
                        }
                    }
                }
                )
    }

    init {
        _onLogin.value = false
    }

    fun setUser(userName: String, password: String) {
        _logReq.value = LoginRequest(userName, password)
        getUserFromBackend()
    }

    fun setOnLogin(canLogin: Boolean) {
        _onLogin.value = canLogin
    }

}
