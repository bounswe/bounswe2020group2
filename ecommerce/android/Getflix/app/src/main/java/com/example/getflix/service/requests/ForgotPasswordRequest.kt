package com.example.getflix.service.requests

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForgotPasswordRequest(
    val password: String,
    @SerializedName("new_password")
    var newPassword: String
) : Parcelable