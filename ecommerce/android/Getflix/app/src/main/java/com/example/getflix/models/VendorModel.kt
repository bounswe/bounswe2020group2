package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorModel (

        @SerializedName("rating") val rating : Double,
        @SerializedName("id") val id : Int,
        @SerializedName("name") val name : String
) : Parcelable