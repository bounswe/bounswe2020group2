package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ReviewedByModel (

    @SerializedName("id") val id : Int,
    @SerializedName("firstname") val firstname : String,
    @SerializedName("lastname") val lastname : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(firstname)
        parcel.writeString(lastname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReviewedByModel> {
        override fun createFromParcel(parcel: Parcel): ReviewedByModel {
            return ReviewedByModel(parcel)
        }

        override fun newArray(size: Int): Array<ReviewedByModel?> {
            return arrayOfNulls(size)
        }
    }
}