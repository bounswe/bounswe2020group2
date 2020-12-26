package com.example.getflix.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class CardSingleModel(
        @SerializedName("status")
        val status: Status,
        @SerializedName("card")
        val card:CardModel,
)