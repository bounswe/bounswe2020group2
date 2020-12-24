package com.example.getflix.service.requests

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginRequest(var username: String, var password: String) :Parcelable
