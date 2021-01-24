package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListProductModel (

    @SerializedName("id")
    val id : Int,
    @SerializedName("product")
    val product : ProductModel
) : Parcelable