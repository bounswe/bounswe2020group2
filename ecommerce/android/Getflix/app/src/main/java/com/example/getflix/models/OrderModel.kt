package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable

data class OrderModel(
    val id : Int,
    val amount : Int,
    val unit_price : Double,
    val total_price : Double,
    val status : String,
    val purchase_date : String,
    val address: AddressModel?,
    val product: ProductModel?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(AddressModel::class.java.classLoader),
        parcel.readParcelable(ProductModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(amount)
        parcel.writeDouble(unit_price)
        parcel.writeDouble(total_price)
        parcel.writeString(status)
        parcel.writeString(purchase_date)
        parcel.writeParcelable(address, flags)
        parcel.writeParcelable(product, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderModel> {
        override fun createFromParcel(parcel: Parcel): OrderModel {
            return OrderModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderModel?> {
            return arrayOfNulls(size)
        }
    }

    fun priceToString(): String {
        return total_price.toString()
    }

    fun amountToString(): String {
        return amount.toString()
    }
}