package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressSingleModel(
        @SerializedName("status")
        val status: Status,
        @SerializedName("address")
        val sc_item: AddressModel,
) : Parcelable
