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


    private val _onMailVerificationSent = MutableLiveData<Boolean>()
    val onMailVerificationSent: LiveData<Boolean>
        get() = _onMailVerificationSent

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user


    private fun getUserFromBackend(fragment: Fragment) {

        GetflixApi.getflixApiService.getUserInformation(_logReq.value!!)
            .enqueue(object :
                Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    println("onFailure")
                    /*      val currentUser = call.execute().body()?.user
                          if (currentUser != null) {
                              println("onFailure")
                              auth.signInWithEmailAndPassword(
                                  _logReq.email,
                                  _logReq.value!!.password
                              ).addOnCompleteListener {
                                  if (it.isSuccessful) {
                                      _onLogin.value = true

                                  } else {
                                      _user.value = null
                                  }
                              }
                          } else {

                              _user.value = null
                          }*/
                    _user.value = null
                    _logReq.value = null
                    println("On failure")
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>

                ) {
                    println("On response giriş :" + response.body())
                    if (response.body() != null && response.body()!!.status.message == "Giriş başarılı") {
                        val currentUser = response.body()?.user
                        if (currentUser != null) {
                            auth.signInWithEmailAndPassword(
                                currentUser.email,
                                _logReq.value!!.password
                            ).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    //println("Loginde firebase giriş")
                                    _user.value = currentUser
                                } else {
                                    //println(it.exception?.message)
                                    //println("Loginde firebase giriş olmadı")
                                    _user.value = null
                                }
                            }
                            if (currentUser.isVerified) {
                                _onMailVerificationSent.value = false
                            } else {
                                _onMailVerificationSent.value = true
                                verifyMailAddress()
                            }
                        } else {
                            Toast.makeText(
                                fragment.requireContext(), response.message(), Toast.LENGTH_LONG
                            )
                            //println("Login fragmenntta response null olduğu için giremedi")
                            _user.value = null
                        }
                    } else {
                        //println("Login fragmenntta response body giriş hiç olmadı " + response.body())
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

    fun verifyMailAddress() {
        val firebaseUser = auth.currentUser
        if (firebaseUser?.isEmailVerified == false) {
            firebaseUser.let {
                it.sendEmailVerification().addOnSuccessListener {
                    //println("Şu an fire base register maili attı")
                    _onMailVerificationSent.value = true
                }.addOnFailureListener {
                    //println("Şu an firebase register maili atamadı")
                    _onMailVerificationSent.value = false
                }
            }
        } else {
            _user.value?.isVerified = true
            MainActivity.StaticData.user?.isVerified = true
            _onMailVerificationSent.value = false
        }
    }

    init {
        _onMailVerificationSent.value = false
    }


    fun setUser(fragment: Fragment, userName: String, password: String) {
        _logReq.value = LoginRequest(userName, password)
        getUserFromBackend(fragment)
    }

    fun onMailVerificationComplete() {
        _onMailVerificationSent.value = false
    }
}
