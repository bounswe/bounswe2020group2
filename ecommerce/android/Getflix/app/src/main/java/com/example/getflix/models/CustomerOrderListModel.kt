package com.example.getflix.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerOrderListModel(
    @SerializedName("status") val status: Status,
    @SerializedName("orders") val cartProducts: List<OrderModel>
) : Parcelable