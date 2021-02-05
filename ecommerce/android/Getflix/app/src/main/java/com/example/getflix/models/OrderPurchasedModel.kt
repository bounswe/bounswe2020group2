package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderPurchasedModel(
    @SerializedName("id") val id : Int,
    @SerializedName("amount") val amount : Int,
    @SerializedName("product") val product : ProductModel,
    @SerializedName("status") val status : String,
    @SerializedName("unit_price") val unit_price : Double,
    @SerializedName("purchase_date") val purchase_date : String,
    @SerializedName("vendor") val vendor : VendorModel,
    @SerializedName("address") val address : AddressModel
) : Parcelable