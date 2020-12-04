package com.example.getflix.services

import com.example.getflix.models.PModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsAPI {

     @GET("products/homepage/{numberOfProducts}")
     suspend fun getProducts(@Path("numberOfProducts") numberOfProducts: Int): Response<List<PModel>>

     @GET("product/{productId}")
     suspend fun getProduct(@Path("productId") id: Int): Response<List<PModel>>

     @GET("user/{userId}/listShoppingCart")
     suspend fun userCartProducts(@Path("userId") id: Int): Response<List<PModel>>


}