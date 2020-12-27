package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderModel(
    @SerializedName("order_id") val id : Int,
    @SerializedName("order_all_purchase") val order_all_purchase : List<OrderPurchasedModel>

) : Parcelable