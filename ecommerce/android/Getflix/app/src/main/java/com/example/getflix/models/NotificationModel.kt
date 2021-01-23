package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationModel(
    @SerializedName("id") val id : Int,
    @SerializedName("type") val type : String,
    @SerializedName("is_seen") val isSeen : Boolean,
    @SerializedName("date") val date : String,
    @SerializedName("argument") val argument : ArgumentModel
) : Parcelable