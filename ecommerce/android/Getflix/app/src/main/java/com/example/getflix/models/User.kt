package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        var token: String,
        var id: Int,
        var email: String,
        @SerializedName("firstname")
        var firstName: String,
        @SerializedName("lastname")
        var lastName: String,
        @SerializedName("role")
        var role: String

) : Parcelable

