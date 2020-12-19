package com.example.getflix.services.responses

import android.os.Parcelable
import com.example.getflix.models.Status
import com.example.getflix.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    var status: Status,
    var user: User
) : Parcelable
