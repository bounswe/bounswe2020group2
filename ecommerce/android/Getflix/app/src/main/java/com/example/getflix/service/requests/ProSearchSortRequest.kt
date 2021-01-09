package com.example.getflix.service.requests

import com.google.gson.annotations.SerializedName

data class ProSearchSortRequest(
    @SerializedName("sort_by")
    var sortBy: String,
    @SerializedName("sort_order")
    var sortOrder: String,
    var subcategory: Int?
)