package com.example.getflix.models

data class ProductModel(
    var id: Int,
    var name: String,
    var category: String,
    var price: Float,
    var brandId: String,
    var description: String
)