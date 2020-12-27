package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CardModel(

        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("owner_name") val owner_name: String,
        @SerializedName("serial_number") val serial_number: String,
        @SerializedName("expiration_date") val expiration_data: ExpirationDateModel,
        @SerializedName("cvv") val cvv: Int
) : Parcelable

