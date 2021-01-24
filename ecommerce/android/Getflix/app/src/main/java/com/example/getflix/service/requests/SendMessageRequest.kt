package com.example.getflix.service.requests

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendMessageRequest(
    @SerializedName("receiver_id") val receiver_id: Int,
    @SerializedName("text") val text: String,
    @SerializedName("attachment") val attachment: String?
) : Parcelable