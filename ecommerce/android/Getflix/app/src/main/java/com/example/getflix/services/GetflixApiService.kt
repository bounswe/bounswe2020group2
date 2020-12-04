package com.example.getflix.services

import com.example.getflix.models.LoginRequest
import com.example.getflix.models.LoginResponse
import com.example.getflix.models.PModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val BASE_URL = "http://10.0.2.2:8000/"

private val requestInterceptor = Interceptor { chain ->

    val url = chain.request()
            .url()
            .newBuilder()
            .build()

    val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
    return@Interceptor chain.proceed(request)
}

private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .build()


private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


interface GetflixApiService {


    @Headers("Content-Type: application/json")
    @POST("regularlogin")
    fun getUserInformation(@Body userData: LoginRequest): Call<LoginResponse>


}

object GetflixApi {
    val getflixApiService: GetflixApiService by lazy { retrofit.create(GetflixApiService::class.java) }
}
