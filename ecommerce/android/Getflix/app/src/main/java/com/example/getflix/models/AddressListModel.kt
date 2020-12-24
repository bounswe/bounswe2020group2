package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressListModel(
        @SerializedName("status") val status: Status,
        @SerializedName("addresses") val addresses: List<AddressModel>
) : Parcelable