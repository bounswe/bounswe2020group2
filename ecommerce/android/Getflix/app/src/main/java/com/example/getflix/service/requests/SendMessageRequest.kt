package com.example.getflix.service.requests

import com.google.gson.annotations.SerializedName

data class SendMessageRequest(
    @SerializedName("receiver_id")
    var receiverId: Int,
    var text: String?,
    var attachment: String?
)