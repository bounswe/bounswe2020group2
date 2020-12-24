package com.example.getflix.models

import com.google.gson.annotations.SerializedName


data class CategoryListModel(
    @SerializedName("categories") val categories: List<CategoryModel>
)