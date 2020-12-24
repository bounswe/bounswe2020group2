package com.example.getflix.models

import com.google.gson.annotations.SerializedName


data class CartProductListModel (
    @SerializedName("status") val status : Status,
    @SerializedName("sc_items") val cartProducts : List<CartProductModel>)