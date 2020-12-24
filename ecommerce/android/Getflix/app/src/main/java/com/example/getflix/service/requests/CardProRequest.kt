package com.example.getflix.service.requests

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardProRequest(var amount: Int, var productId: Int) : Parcelable