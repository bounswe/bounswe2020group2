package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArgumentModel (

    @SerializedName("purchase_id") val pid : Int,
    @SerializedName("old_status") val oldStatus : String,
    @SerializedName("new_status") val newStatus : String,
    @SerializedName("product") val product : ProductModel,
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("old_price") val old_price : Double,
    @SerializedName("new_price") val new_price : Double,
    @SerializedName("short_description") val short_description : String,
    @SerializedName("image_url") val image_url : String,
    @SerializedName("vendor") val vendor : VendorModel
) : Parcelable