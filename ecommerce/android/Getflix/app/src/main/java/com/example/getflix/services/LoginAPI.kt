package com.example.getflix.services

import retrofit2.Call
import retrofit2.http.*

interface LoginAPI {

    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST( "login/")
    fun logUser(@Field(value = "email") email: String,
                @Field(value = "password") password: String): Call<DefaultResponse>

}