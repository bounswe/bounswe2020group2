package com.example.getflix.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Status(
        var succcesful: Boolean,
        var message: String
) : Parcelable
