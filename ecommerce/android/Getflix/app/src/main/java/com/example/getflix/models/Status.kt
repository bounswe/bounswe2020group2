package com.example.getflix.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Status(
        var successful: Boolean,
        var message: String ?=null
) : Parcelable
