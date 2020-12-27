package com.example.getflix.service.requests

import com.example.getflix.models.ExpirationDateModel
import com.google.gson.annotations.SerializedName


data class CustomerCheckoutRequest(
    @SerializedName("address_id") val addressId: Int,
    @SerializedName("card_id") val cardId: Int,

)