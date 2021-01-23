package com.example.getflix.service.requests

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorOrderStatusRequest (
    val orderId : Int,
    val orderStatus : String
) : Parcelable