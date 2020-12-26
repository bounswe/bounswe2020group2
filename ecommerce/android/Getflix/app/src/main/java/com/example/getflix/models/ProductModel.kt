package com.example.getflix.models
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class ProductModel (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("price") val price : Double,
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
    @SerializedName("rating") val rating : Double,
    @SerializedName("images") val images : List<String>,
    @SerializedName("price_after_discount") val priceDiscounted : Double,
    @SerializedName("is_deleted") val deleted : Boolean
) : Parcelable