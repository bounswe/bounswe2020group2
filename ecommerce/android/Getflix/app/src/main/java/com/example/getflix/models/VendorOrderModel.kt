package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorOrderModel (
    @SerializedName("id") val id : Int,
    @SerializedName("amount") val amount : Int,
    @SerializedName("product") val product : ProductModel,
    @SerializedName("status") val status : String,
    @SerializedName("unit_price") val unitPrice : Int,
    @SerializedName("total_price") val totalPrice : Int,
    @SerializedName("purchase_date") val purchaseDate : String,
    @SerializedName("address") val address : AddressModel
) :Parcelable