package com.example.getflix.models

import com.google.gson.annotations.SerializedName

data class RecommendationModel (

    @SerializedName("status") val status : Status,
    @SerializedName("products") val products : List<ProductModel>
)