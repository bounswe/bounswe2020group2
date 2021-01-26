package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageModel (

    @SerializedName("id") val id : Int,
    @SerializedName("text") val text : String,
    @SerializedName("sent_by_me") var sentByMe : Boolean,
    @SerializedName("date") val date : String,
    @SerializedName("attachment_url") val attachmentUrl : String
) : Parcelable