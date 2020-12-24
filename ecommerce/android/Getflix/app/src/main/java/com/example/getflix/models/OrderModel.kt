package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderModel(
    val id : Int,
    val amount : Int,
    val unit_price : Double,
    val total_price : Double,
    val status : String,
    val purchase_date : String,
    val address: AddressModel?,
    val product: ProductModel?

) : Parcelable