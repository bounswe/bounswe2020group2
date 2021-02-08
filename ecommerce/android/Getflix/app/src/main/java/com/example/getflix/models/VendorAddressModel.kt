package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorAddressModel(
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