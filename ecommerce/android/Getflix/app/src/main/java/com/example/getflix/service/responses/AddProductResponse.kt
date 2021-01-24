package com.example.getflix.service.responses

import com.example.getflix.models.Status
import com.google.gson.annotations.SerializedName

data class AddProductResponse (

    @SerializedName("id") val id : Int,
    @SerializedName("status") val status : Status
)