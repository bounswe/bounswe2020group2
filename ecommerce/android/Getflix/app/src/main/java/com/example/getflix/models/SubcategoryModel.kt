package com.example.getflix.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class SubcategoryModel (

        @SerializedName("name") val name : String,
        @SerializedName("id") val id : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
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