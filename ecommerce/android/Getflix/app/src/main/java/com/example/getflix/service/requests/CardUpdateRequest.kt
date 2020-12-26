package com.example.getflix.service.requests

import android.os.Parcelable
import com.example.getflix.models.ExpirationDateModel
import com.example.getflix.models.PhoneModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class CardUpdateRequest(
        @SerializedName("name") val name: String,
        @SerializedName("owner_name") val owner_name: String,
        @SerializedName("serial_number") val serial_number: String,
        @SerializedName("expiration_date") val expiration_data: ExpirationDateModel,
        @SerializedName("cvv") val cvv: Int
)