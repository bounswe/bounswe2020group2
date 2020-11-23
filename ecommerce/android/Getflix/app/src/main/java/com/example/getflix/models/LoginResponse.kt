package com.example.getflix.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
        var status: Status,
        var user: User
) : Parcelable
