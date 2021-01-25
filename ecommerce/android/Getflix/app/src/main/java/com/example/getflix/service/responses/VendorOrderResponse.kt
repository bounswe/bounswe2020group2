package com.example.getflix.service.responses

import android.os.Parcelable
import com.example.getflix.models.Status
import com.example.getflix.models.VendorOrderModel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class VendorOrderResponse (
    val status : Status,
    var orders : List<VendorOrderModel>
) : Parcelable