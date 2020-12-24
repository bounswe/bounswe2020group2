package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.Status
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardProDeleteResponse(var status: Status): Parcelable