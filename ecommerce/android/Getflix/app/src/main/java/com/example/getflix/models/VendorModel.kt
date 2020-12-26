package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class VendorModel (

        @SerializedName("rating") val rating : Double,
        @SerializedName("id") val id : Int,
        @SerializedName("name") val name : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(rating)
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VendorModel> {
        override fun createFromParcel(parcel: Parcel): VendorModel {
            return VendorModel(parcel)
        }

        override fun newArray(size: Int): Array<VendorModel?> {
            return arrayOfNulls(size)
        }
    }
}