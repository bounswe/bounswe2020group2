package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductReviewListModel (

    @SerializedName("reviews") val reviews : List<ReviewModel>,
    @SerializedName("status") val status : Status
) : Parcelable