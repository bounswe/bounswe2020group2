package com.example.getflix.services

import com.example.getflix.activities.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    fun tryLogin(userData: LogReq, onResult: (LogReq?) -> Unit) {
        val retrofit = Retrofit.Builder().baseUrl(MainActivity.StaticData.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginAPI::class.java)

        retrofit.logUser(userData).enqueue(
            object : Callback<LogReq> {
                override fun onFailure(call: Call<LogReq>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<LogReq>, response: Response<LogReq>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                    if(response.isSuccessful) {
                        println(response.body())
                    }
                }
            }
        )
    }
}