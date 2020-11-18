package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup


data class CategoryModel(
    var id: Int,
    var name: String?,
    var subCats: List<SubcategoryModel>
) :  ExpandableGroup<SubcategoryModel>(name, subCats) {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.createTypedArrayList(SubcategoryModel)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeTypedList(subCats)
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
}