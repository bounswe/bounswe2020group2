package com.example.getflix.models


import android.os.Parcel
import android.os.Parcelable

data class SubcategoryModel(
    var id: Int,
    var catId: Int,
    var name: String?,
    var products: List<ProductModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        TODO("products")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(catId)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubcategoryModel> {
        override fun createFromParcel(parcel: Parcel): SubcategoryModel {
            return SubcategoryModel(parcel)
        }

        override fun newArray(size: Int): Array<SubcategoryModel?> {
            return arrayOfNulls(size)
        }
    }

}