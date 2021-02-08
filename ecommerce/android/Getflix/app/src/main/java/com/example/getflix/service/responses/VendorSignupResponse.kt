package com.example.getflix.service.responses

import com.google.gson.annotations.SerializedName

data class VendorSignupResponse (

    @SerializedName("successful") val successful : Boolean,
    @SerializedName("message") val message : String
)