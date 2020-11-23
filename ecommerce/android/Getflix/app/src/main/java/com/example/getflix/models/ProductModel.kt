package com.example.getflix.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("price")
    val price : Int?,
    @SerializedName("category")
    val category : String?,
    @SerializedName("description")
    val description : String?) : Parcelable {

}


