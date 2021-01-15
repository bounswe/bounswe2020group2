package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryListModel(
    @SerializedName("categories") val categories: List<CategoryModel>
) : Parcelable