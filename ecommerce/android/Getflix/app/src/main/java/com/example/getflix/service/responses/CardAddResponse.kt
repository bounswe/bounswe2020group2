package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.Status
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardAddResponse(
        @SerializedName("card_id")
        var cardId: Int,
        @SerializedName("status")
        var status: Status
) : Parcelable