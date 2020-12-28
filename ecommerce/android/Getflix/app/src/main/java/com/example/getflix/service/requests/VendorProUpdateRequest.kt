package com.example.getflix.service.requests

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorProUpdateRequest(

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("price") val price : Double,
    @SerializedName("stock_amount") val stock_amount : Int,
    @SerializedName("short_description") val shortDescription : String,
    @SerializedName("long_description") val longDescription : String,
    @SerializedName("discount") val discount : Double,
    @SerializedName("brand_id") val brand_id : Int,
    @SerializedName("subcategory_id") val subcategory_id : Int
) : Parcelable