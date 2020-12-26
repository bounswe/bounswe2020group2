package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaginationModel(
    @SerializedName("page") val page : Int,
    @SerializedName("page_size") val pageSize : Int,
    @SerializedName("total_items") val totalItems : Int
) : Parcelable