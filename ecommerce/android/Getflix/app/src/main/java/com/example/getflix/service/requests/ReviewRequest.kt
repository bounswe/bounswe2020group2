package com.example.getflix.service.requests

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("vendor_id") val vendorId: Int,
    val rating: Int,
    val comment: String

) : Parcelable