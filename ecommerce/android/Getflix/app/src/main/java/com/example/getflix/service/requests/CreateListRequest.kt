package com.example.getflix.service.requests


import com.google.gson.annotations.SerializedName

data class CreateListRequest(
    @SerializedName("name") val name: String
)