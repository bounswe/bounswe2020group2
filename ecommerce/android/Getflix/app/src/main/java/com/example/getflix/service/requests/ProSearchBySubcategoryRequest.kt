package com.example.getflix.service.requests

import com.google.gson.annotations.SerializedName

data class ProSearchBySubcategoryRequest(
    @SerializedName("query")
    var query: String?,
    @SerializedName("subcategory")
    var subcategory: Int?,
    @SerializedName("vendor")
    var vendor: Int?,
    @SerializedName("min_rating")
    var rating: Double?,
    @SerializedName("max_price")
    var price: Double?,
    @SerializedName("sort_by")
    var sortBy: String?,
    @SerializedName("sort_order")
    var sortOrder: String?,
)