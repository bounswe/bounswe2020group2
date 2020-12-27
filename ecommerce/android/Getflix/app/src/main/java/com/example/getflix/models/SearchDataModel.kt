package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchDataModel(
    @SerializedName("pagination") val pagination : PaginationModel,
    @SerializedName("products") val products : List<ProductModel>
) : Parcelable