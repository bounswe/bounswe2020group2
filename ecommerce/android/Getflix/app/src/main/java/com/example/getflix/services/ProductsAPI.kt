package com.example.getflix.services

import com.example.getflix.models.PModel
import com.example.getflix.models.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {

     @GET("products/homepage/3?format=json")
     suspend fun getProducts(): Response<List<PModel>>




}