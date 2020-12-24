package com.example.getflix.service.requests

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpRequest(
        val username: String,
        val email: String,
        val password: String,
        @SerializedName("firstname")
        val firstName: String,
        @SerializedName("lastname")
        val lastName: String,
        @SerializedName("phonenumber")
        val phoneNumber: String
) : Parcelable
