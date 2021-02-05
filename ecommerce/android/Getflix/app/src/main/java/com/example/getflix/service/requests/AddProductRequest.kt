package com.example.getflix.service.requests

import com.google.gson.annotations.SerializedName

data class AddProductRequest(

    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("stock_amount") val stock_amount: Int,
    @SerializedName("short_description") val short_description: String,
    @SerializedName("long_description") val long_description: String,
    @SerializedName("discount") val discount: Double,
    @SerializedName("brand_id") val brand_id: Int,
    @SerializedName("subcategory_id") val subcategory_id: Int,
    @SerializedName("images") val images: List<String>
)