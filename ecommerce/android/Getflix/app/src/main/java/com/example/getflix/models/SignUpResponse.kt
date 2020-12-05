package com.example.getflix.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpResponse(
        val successful: Boolean,
        val message: String
) : Parcelable
