package com.example.getflix.models

import com.google.gson.annotations.SerializedName

data class MessageListModel(

    @SerializedName("status") val status : Status,
    @SerializedName("conversations") val conversations : List<ConversationModel>
)