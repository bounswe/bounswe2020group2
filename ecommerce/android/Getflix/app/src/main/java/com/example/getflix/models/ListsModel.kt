package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListsModel(
    @SerializedName("status") val status : Status,
    @SerializedName("lists") val lists : List<ListModel>
) : Parcelable