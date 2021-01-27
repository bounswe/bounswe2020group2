package com.example.getflix.ui.viewmodels


import android.view.View
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.auth
import com.example.getflix.infoAlert
import com.example.getflix.models.*
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.LoginRequest
import com.example.getflix.service.responses.LoginResponse
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    private val _logReq = MutableLiveData<LoginRequest?>()


    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user


    private fun getUserFromBackend(fragment: Fragment) {

        GetflixApi.getflixApiService.getUserInformation(_logReq.value!!)
            .enqueue(object :
                Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    _user.value = null
                    _logReq.value = null
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>

                ) {
                    if (response.body() != null && response.body()!!.status.message == "Giriş başarılı") {
                        val currentUser = response.body()?.user!!


                            auth.signInWithEmailAndPassword(
                                currentUser.email,
                                _logReq.value!!.password
                            ).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    _user.value = currentUser
                                } else {
                                    _user.value = null
                                }
                            }


                    }
                    else {
                        _user.value = null
                        infoAlert(
                            fragment,
                            fragment.requireContext().getString(R.string.login_info)
                        )
                        // fragment.requireActivity().loading_progress!!.visibility = View.GONE
                    }
                }
            }
            )
    }


    fun setUser(fragment: Fragment, userName: String, password: String) {
        _logReq.value = LoginRequest(userName, password)
        getUserFromBackend(fragment)
    }

    fun onMailVerificationComplete() {
        _user.value = null
    }
}
