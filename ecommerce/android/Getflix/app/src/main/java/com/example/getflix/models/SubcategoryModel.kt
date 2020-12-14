package com.example.getflix.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SubcategoryModel(
    var name: String?,
    var products: MutableList<ProductModel>?
) : Parcelable