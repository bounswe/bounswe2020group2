package com.example.getflix.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PModel (

    val id : Int,
    val name : String,
    val price : Int,
    val creation_date : String,
    val image_url : String,
    val total_rating : Int,
    val rating_count : Int,
    val stock_amount : Int,
    val description : String,
    val subcategory : String,
    val category : String,
    val brand : String,
    val vendor : String
) : Parcelable