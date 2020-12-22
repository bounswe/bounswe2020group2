package com.example.getflix.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CategoryModel (
        @SerializedName("name") val name : String,
        @SerializedName("id") val id : Int,
        @SerializedName("subcategories") val subcategories : MutableList<SubcategoryModel>
) : ExpandableGroup<SubcategoryModel>(name, subcategories) {


    operator fun get(s: String): MutableList<SubcategoryModel>? {
        return null
    }
}