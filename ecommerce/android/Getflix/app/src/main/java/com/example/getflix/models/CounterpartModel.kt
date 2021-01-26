package com.example.getflix.models

import com.google.gson.annotations.SerializedName

data class CounterpartModel (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)