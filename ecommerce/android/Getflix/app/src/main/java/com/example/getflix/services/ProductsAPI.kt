package com.example.getflix.services

import com.example.getflix.models.PModel
import com.example.getflix.models.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {

     @GET("products/homepage/3")
     suspend fun getProducts(): Response<List<PModel>>




}