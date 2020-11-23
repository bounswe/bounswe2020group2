package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.example.getflix.models.SubcategoryModel
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryModel(
    var name: String?,
    var subCats: MutableList<SubcategoryModel>
) :  ExpandableGroup<SubcategoryModel>(name, subCats) {

    operator fun get(s: String): MutableList<SubcategoryModel>? {
        return null
    }

}