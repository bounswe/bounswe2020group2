package com.example.getflix.services.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpResponse(
        val successful: Boolean,
        val message: String
) : Parcelable
