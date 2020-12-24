package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartProductModel (

    @SerializedName("id")
    val id : Int,
    @SerializedName("amount")
    val amount : Int,
    @SerializedName("product")
    val product : ProductModel
) : Parcelable