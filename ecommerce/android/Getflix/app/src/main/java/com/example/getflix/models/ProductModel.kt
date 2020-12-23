package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ProductModel (


        @SerializedName("id") val id : Int,
        @SerializedName("name") val name : String,
        @SerializedName("price") val price : Int,
        @SerializedName("creation_date") val creation_date : String,
        @SerializedName("total_rating") val total_rating : Int,
        @SerializedName("rating_count") val rating_count : Int,
        @SerializedName("stock_amount") val stock_amount : Int,
        @SerializedName("short_description") val short_description : String,
        @SerializedName("subcategory") val subcategory : SubcategoryModel,
        @SerializedName("long_description") val long_description : String,
        @SerializedName("discount") val discount : Double,
        @SerializedName("category") val category : CategoryModel,
        @SerializedName("brand") val brand : BrandModel,
        @SerializedName("vendor") val vendor : VendorModel,
        @SerializedName("rating") val rating : Int,
        @SerializedName("images") val images : List<String>,
        @SerializedName("old_price") val old_price : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readParcelable(SubcategoryModel::class.java.classLoader)!!,
            parcel.readString()!!,
            parcel.readDouble(),
            parcel.readParcelable(CategoryModel::class.java.classLoader)!!,
            parcel.readParcelable(BrandModel::class.java.classLoader)!!,
            parcel.readParcelable(VendorModel::class.java.classLoader)!!,
            parcel.readInt(),
            parcel.createStringArrayList()!!,
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeString(creation_date)
        parcel.writeInt(total_rating)
        parcel.writeInt(rating_count)
        parcel.writeInt(stock_amount)
        parcel.writeString(short_description)
        parcel.writeParcelable(subcategory, flags)
        parcel.writeString(long_description)
        parcel.writeDouble(discount)
        parcel.writeParcelable(category, flags)
        parcel.writeParcelable(brand, flags)
        parcel.writeParcelable(vendor, flags)
        parcel.writeInt(rating)
        parcel.writeStringList(images)
        parcel.writeInt(old_price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }

}