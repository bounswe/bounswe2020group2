package com.example.getflix.models

import com.google.gson.annotations.SerializedName

data class CartProductModel (

    @SerializedName("id")
    val id : Int,
    @SerializedName("amount")
    val amount : Int,
    @SerializedName("product")
    val product : ProductModel
)