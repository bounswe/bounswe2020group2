package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.SearchDataModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProSearchBySubcategoryResponse(
    @SerializedName("data") val data : SearchDataModel
) : Parcelable