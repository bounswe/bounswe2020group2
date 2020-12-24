package com.example.getflix.service.requests

import android.os.Parcelable
import com.example.getflix.models.PhoneModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class AddressAddRequest(
        @SerializedName("title")
        var title: String,
        @SerializedName("phone")
        var phone: PhoneModel,
        @SerializedName("name")
        var name: String,
        @SerializedName("surname")
        var surname: String,
        @SerializedName("address")
        var address: String,
        @SerializedName("province")
        var province: String,
        @SerializedName("city")
        var city: String,
        @SerializedName("country")
        var country: String,
        @SerializedName("zip_code")
        var zipCode: String
) : Parcelable