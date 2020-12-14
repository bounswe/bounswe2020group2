package com.example.getflix.services

import com.example.getflix.models.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val BASE_URL = "http://ec2-18-189-28-20.us-east-2.compute.amazonaws.com:8000/"

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


    @Headers("Content-Type: application/json")
    @POST("regularsignup")
    fun signUp(@Body signUpCredentials: SignUpCredentials): Call<SignUpResponse>

    @GET("products/homepage/{numberOfProducts}")
    suspend fun getProducts(@Path("numberOfProducts") numberOfProducts: Int): Response<List<ProductModel>>

    @GET("product/{productId}")
    suspend fun getProduct(@Path("productId") productId: Int): Response<List<ProductModel>>

    @GET("user/{userId}/listShoppingCart")
    suspend fun userCartProducts(@Path("userId") userId: Int): Response<List<ProductModel>>

    @Headers("Content-Type: application/json")
    @POST("user/{userId}/shoppingCart")
    fun addCartProduct(@Path("userId") userId: Int,@Body userData: CardProRequest): Call<CardProResponse>


}

object GetflixApi {
    val getflixApiService: GetflixApiService by lazy { retrofit.create(GetflixApiService::class.java) }
}
