package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class CardListModel(
        @SerializedName("status") val status: Status,
        @SerializedName("cards") val addresses: List<CardModel>
)