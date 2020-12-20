package com.example.getflix.models

import com.google.gson.annotations.SerializedName

data class ExpirationDateModel (

        @SerializedName("month") val month : Int,
        @SerializedName("year") val year : Int
)