package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ListModel(
    @SerializedName("list_id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("products") val products: List<ListProductModel>
) : Parcelable