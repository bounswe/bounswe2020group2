package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BrandModel (

        @SerializedName("name") val name : String,
        @SerializedName("id") val id : Int
) : Parcelable

