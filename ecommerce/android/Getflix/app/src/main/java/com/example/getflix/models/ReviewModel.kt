package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ReviewModel (

    @SerializedName("id") val id : Int,
    @SerializedName("rating") val rating : Int,
    @SerializedName("comment") val comment : Int,
    @SerializedName("product") val product : ProductModel,
    @SerializedName("vendor") val vendor : VendorModel,
    @SerializedName("review_date") val review_date : String,
    @SerializedName("reviewed_by") val reviewed_by : ReviewedByModel
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(ProductModel::class.java.classLoader)!!,
        parcel.readParcelable(VendorModel::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readParcelable(ReviewedByModel::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(rating)
        parcel.writeInt(comment)
        parcel.writeParcelable(product, flags)
        parcel.writeParcelable(vendor, flags)
        parcel.writeString(review_date)
        parcel.writeParcelable(reviewed_by, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReviewModel> {
        override fun createFromParcel(parcel: Parcel): ReviewModel {
            return ReviewModel(parcel)
        }

        override fun newArray(size: Int): Array<ReviewModel?> {
            return arrayOfNulls(size)
        }
    }
}