package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.Status
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardProAddResponse(var sc_item_id: Int, var status: Status): Parcelable