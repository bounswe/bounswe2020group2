package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewedByModel (

    @SerializedName("id") val id : Int,
    @SerializedName("firstname") val firstname : String,
    @SerializedName("lastname") val lastname : String
) : Parcelable