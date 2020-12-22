package com.example.getflix.service

import com.example.getflix.models.*
import com.example.getflix.service.requests.CardProAddRequest
import com.example.getflix.service.requests.CardProUpdateRequest
import com.example.getflix.service.requests.LoginRequest
import com.example.getflix.service.requests.SignUpRequest
import com.example.getflix.service.responses.CardProAddResponse
import com.example.getflix.service.responses.CardProUpdateResponse
import com.example.getflix.service.responses.LoginResponse
import com.example.getflix.service.responses.SignUpResponse
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
    fun signUp(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @GET("products/homepage/{numberOfProducts}")
    suspend fun getProducts(@Path("numberOfProducts") numberOfProducts: Int): Response<List<ProductModel>>

    @GET("product/{productId}")
    suspend fun getProduct(@Path("productId") productId: Int): Response<List<ProductModel>>


    @GET("customer/{customerId}/shoppingcart")
    suspend fun getCustomerCartProducts(@Header("Authorization") token: String, @Path("customerId") customerId: Int): Response<CartProductListModel>

    @Headers("Content-Type: application/json")
    @POST("customer/{customerId}/shoppingcart")
    fun addCustomerCartProduct(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Body cardproData: CardProAddRequest): Call<CardProAddResponse>

    @Headers("Content-Type: application/json")
    @PUT("customer/{customerId}/shoppingcart/{sc_item_id}")
    fun updateCustomerCartProduct(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("sc_item_id") sc_item_id: Int,@Body cardproData: CardProUpdateRequest): Call<CardProUpdateResponse>

    @GET("categories")
    suspend fun getCategories(): Response<CategoryListModel>

}

object GetflixApi {
    val getflixApiService: GetflixApiService by lazy { retrofit.create(GetflixApiService::class.java) }
}
