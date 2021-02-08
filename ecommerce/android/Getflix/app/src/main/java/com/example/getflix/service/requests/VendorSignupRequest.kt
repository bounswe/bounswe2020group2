package com.example.getflix.service.requests

import android.os.Parcelable
import com.example.getflix.models.AddressModel
import com.example.getflix.models.VendorAddressModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorSignupRequest (

    @SerializedName("username") val username : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("firstname") val firstname : String,
    @SerializedName("lastname") val lastname : String,
    @SerializedName("phonenumber") val phonenumber : String,
    @SerializedName("address") val address : VendorAddressModel
) : Parcelable