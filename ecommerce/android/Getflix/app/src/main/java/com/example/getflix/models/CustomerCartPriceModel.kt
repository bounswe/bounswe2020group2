package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerCartPriceModel(
    @SerializedName("products_price")
    val productsPrice: Double,
    @SerializedName("delivery_price")
    val deliveryPrice: Double,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("total_price")
    val totalPrice: Double
) : Parcelable