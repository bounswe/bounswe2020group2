package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.Status
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorProUpdateResponse(
    var id: Int,
    var status: Status
) : Parcelable