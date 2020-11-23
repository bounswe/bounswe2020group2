package com.example.getflix.services

import retrofit2.Call
import retrofit2.http.*

interface LoginAPI {

    @Headers("Content-Type: application/json")
    @POST( "regularlogin/")
    fun logUser(@Body userData: LogReq): Call<LogReq>

}