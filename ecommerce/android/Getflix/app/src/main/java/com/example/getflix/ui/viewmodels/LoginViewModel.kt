package com.example.getflix.ui.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.R
import com.example.getflix.infoAlert
import com.example.getflix.models.*
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.LoginRequest
import com.example.getflix.service.responses.LoginResponse

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


    private fun getUserFromBackend(fragment: Fragment) {
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
                        println(response.body()!!.status.message)
                        if (response.body()!!.status.message == "Giriş başarılı") {
                            println(response.body()!!.status.message)
                            _onLogin.value = true
                            _user.value = response.body()!!.user
                        } else {
                            infoAlert(fragment, fragment.requireContext().getString(R.string.login_info))
                        }
                    }
                }
                )
    }

    init {
        _onLogin.value = false
    }

    fun setUser(fragment: Fragment, userName: String, password: String) {
        _logReq.value = LoginRequest(userName, password)
        getUserFromBackend(fragment)
    }

    fun setOnLogin(canLogin: Boolean) {
        _onLogin.value = canLogin
    }

}
