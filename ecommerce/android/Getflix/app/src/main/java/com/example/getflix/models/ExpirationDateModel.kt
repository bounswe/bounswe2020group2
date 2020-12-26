package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExpirationDateModel (

        @SerializedName("month") val month : Int,
        @SerializedName("year") val year : Int
) : Parcelable