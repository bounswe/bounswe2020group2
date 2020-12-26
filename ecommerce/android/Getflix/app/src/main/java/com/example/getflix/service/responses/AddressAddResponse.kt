package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.Status
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressAddResponse(
        @SerializedName("address_id")
        var addressId: Int,
        @SerializedName("status")
        var status: Status
) : Parcelable