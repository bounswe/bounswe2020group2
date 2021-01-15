package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartProductListModel (
    @SerializedName("status") val status : Status,
    @SerializedName("sc_items") val cartProducts : List<CartProductModel>) : Parcelable