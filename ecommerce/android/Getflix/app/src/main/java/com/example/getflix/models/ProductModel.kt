package com.example.getflix.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ProductModel(
    var id: Int,
    var name: String,
    var category: String,
    var price: Float,
    var brandId: String,
    var description: String
) : Parcelable
