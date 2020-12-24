package com.example.getflix.service.requests

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardProAddRequest(var product_id: Int, val amount: Int): Parcelable