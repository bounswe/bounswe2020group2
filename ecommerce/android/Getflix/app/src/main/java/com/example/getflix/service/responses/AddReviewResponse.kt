package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.ReviewModel
import com.example.getflix.models.Status
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddReviewResponse (
    val review: ReviewModel,
    val status : Status
) : Parcelable