package com.example.getflix.models

import com.google.gson.annotations.SerializedName

data class MessageModel (

    @SerializedName("id") val id : Int,
    @SerializedName("text") val text : String,
    @SerializedName("sent_by_me") val sentByMe : Boolean,
    @SerializedName("date") val date : String,
    @SerializedName("attachment_url") val attachmentUrl : String
)