package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

data class CategoryModel (
        @SerializedName("name") val name : String,
        @SerializedName("id") val id : Int,
        @SerializedName("subcategories") val subcategories : List<SubcategoryModel> ?=null
) : Parcelable, ExpandableGroup<SubcategoryModel>(name, subcategories) {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readInt(),
            parcel.createTypedArrayList(SubcategoryModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeTypedList(subcategories)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<CategoryModel> {
        override fun createFromParcel(parcel: Parcel): CategoryModel {
            return CategoryModel(parcel)
        }

        override fun newArray(size: Int): Array<CategoryModel?> {
            return arrayOfNulls(size)
        }
    }

    operator fun get(s: String): MutableList<SubcategoryModel>? {
        return null
    }
}