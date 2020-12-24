package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PhoneModel(
        @SerializedName("country_code") val countryCode : String,
        @SerializedName("number") val number : String

) : Parcelable