package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewModel (

    @SerializedName("id") val id : Int,
    @SerializedName("rating") val rating : Int,
    @SerializedName("comment") val comment : Int,
    @SerializedName("product") val product : ProductModel,
    @SerializedName("vendor") val vendor : VendorModel,
    @SerializedName("review_date") val review_date : String,
    @SerializedName("reviewed_by") val reviewed_by : ReviewedByModel
) : Parcelable