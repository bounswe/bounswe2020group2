package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize



data class CartProductSingleModel (

        @SerializedName("status")
        val status : Status,
        @SerializedName("sc_item")
        val sc_item : CartProductModel,

)