package com.example.getflix.models

import com.google.gson.annotations.SerializedName

data class ConversationModel (

    @SerializedName("counterpart") val counterpart : CounterpartModel,
    @SerializedName("messages") val messages : List<MessageModel>
)