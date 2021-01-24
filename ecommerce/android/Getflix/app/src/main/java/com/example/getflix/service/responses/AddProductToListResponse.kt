package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.Status
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddProductToListResponse(
    @SerializedName("status")
    var status: Status
) : Parcelable