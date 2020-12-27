package com.example.getflix

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.SubcategoryModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.CardProUpdateRequest
import com.example.getflix.service.responses.CardProUpdateResponse
import com.example.getflix.ui.viewmodels.ProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/* - **Electronics**
  - Computers
  - Camera & Photo
  - Cell Phones & Accessories
  - Digital Videos
  - Software
- **Health & Households**
  - Sports & Outdoor
  - Beauty & Personal Care
- **Home & Garden**
  - Luggage
  - Pet Supplies
  - Furniture
- **Clothing**
  - Men's Fashion
  - Women's Fashion
  - Boys' Fashion
  - Girls' Fashion
  - Baby
- **Hobbies**
  - Books
  - Music & CDs
  - Movies & TVs
  - Toys & Games
  - Video Games
  - Arts & Crafts
- **Others**
  - Automotive
  - Industrial & Scientific  */

val categories = listOf<CategoryModel>(
        CategoryModel(
                "Electronics",
                1, listOf(SubcategoryModel("Computers", 1),
                SubcategoryModel("Camera & Photo", 1),
                SubcategoryModel("Cell Phones & Accessories", 1),
                SubcategoryModel("Digital Videos", 1),
                SubcategoryModel("Software", 1)) as MutableList<SubcategoryModel>
        ),
        CategoryModel(
        "Health & Households", 1,
        listOf(SubcategoryModel("Sports & Outdoor", 1),
                SubcategoryModel("Beauty & Personal Care", 1)) as MutableList<SubcategoryModel>
), CategoryModel(
        "Home & Garden", 1,
        listOf(SubcategoryModel("Luggage", 1),
                SubcategoryModel("Pet Supplies", 1),
                SubcategoryModel("Furniture", 1)) as MutableList<SubcategoryModel>
), CategoryModel(
        "Clothing", 1,
        listOf(SubcategoryModel("Men's Fashion", 1),
                SubcategoryModel("Women's Fashion", 1),
                SubcategoryModel("Boys' Fashion", 1),
                SubcategoryModel("Girls' Fashion", 1),
                SubcategoryModel("Baby", 1)) as MutableList<SubcategoryModel>
), CategoryModel(
        "Hobbies", 1,
        listOf(SubcategoryModel("Books", 1),
                SubcategoryModel("Music & CDs", 1),
                SubcategoryModel("Movies & TVs", 1),
                SubcategoryModel("Toys & Games", 1),
                SubcategoryModel("Video Games", 1),
                SubcategoryModel("Arts & Crafts", 1)) as MutableList<SubcategoryModel>
), CategoryModel(
        "Others", 1,
        listOf(SubcategoryModel("Automotive", 1),
                SubcategoryModel("Industrial & Scientific", 1)) as MutableList<SubcategoryModel>
)
)


fun getProductImage(productId: Int): Int {
    return when (productId) {
        1 -> R.drawable.zara_jacket1
        2 -> R.drawable.zara_jacket2
        3 -> R.drawable.zara_jacket3
        4 -> R.drawable.zara_skirt1
        5 -> R.drawable.zara_skirt2
        6 -> R.drawable.zara_skirt3
        7 -> R.drawable.zara_dress3
        8 -> R.drawable.zara_dress2
        9 -> R.drawable.zara_dress1
        10 -> R.drawable.zara_man_jacket1
        11 -> R.drawable.zara_man_jacket2
        12 -> R.drawable.zara_man_jacket3
        13 -> R.drawable.zara_jean1
        14 -> R.drawable.zara_jean2
        15 -> R.drawable.zara_jean3
        16 -> R.drawable.zara_shirt1
        17 -> R.drawable.zara_shirt2
        18 -> R.drawable.zara_shirt3
        else -> R.drawable.zara_skirt3
    }

}
fun getCategoryImage(category: String) : Int {
    return when (category) {
        "Electronics" -> R.drawable.ic_electronics
        "Health & Households" -> R.drawable.ic_health
        "Home & Garden" -> R.drawable.ic_furniture
        "Clothing" -> R.drawable.ic_fashion
        "Hobbies" -> R.drawable.ic_movie
        else -> R.drawable.ic_automative

    }
}

fun infoAlert(fragment: Fragment, message: String) {
    MaterialAlertDialogBuilder(fragment.requireContext(), R.style.MaterialAlertDialog_color)
            .setTitle("Info")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, which ->
            }
            .setIcon(R.drawable.ic_info)
            .show()
}

fun doneAlert(fragment: Fragment, message: String, func: (() -> Unit)) {
    MaterialAlertDialogBuilder(fragment.requireContext(), R.style.MaterialAlertDialog_color)
            .setTitle("Success")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, which ->
                func()
            }
            .setIcon(R.drawable.ic_check)
            .show()
}

fun addToShoppingCart(amount : Int,shoppingCartId: Int, productId: Int) {
    GetflixApi.getflixApiService.updateCustomerCartProduct("Bearer " + MainActivity.StaticData.user!!.token,
        MainActivity.StaticData.user!!.id, shoppingCartId, CardProUpdateRequest(productId,
            amount
        )
    )
        .enqueue(object :
            Callback<CardProUpdateResponse> {
            override fun onFailure(call: Call<CardProUpdateResponse>, t: Throwable) {


fun addToShoppingCart(amount : Int,shoppingCartId: Int, productId: Int) {
    GetflixApi.getflixApiService.updateCustomerCartProduct("Bearer " + MainActivity.StaticData.user!!.token,
        MainActivity.StaticData.user!!.id, shoppingCartId, CardProUpdateRequest(productId,
            amount
        )
    )
        .enqueue(object :
            Callback<CardProUpdateResponse> {
            override fun onFailure(call: Call<CardProUpdateResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<CardProUpdateResponse>,
                response: Response<CardProUpdateResponse>
            ) {

            }
        }
        )
}

fun askAlert(fragment: Fragment, message: String, func: () -> Unit) {
    MaterialAlertDialogBuilder(fragment.requireContext(), R.style.MaterialAlertDialog_color)
            .setTitle(fragment.requireContext().getString(R.string.warning))
            .setMessage(message)
            .setPositiveButton(fragment.requireContext().getString(R.string.yes)) { dialog, which ->
                func()
            }

            .setNegativeButton(fragment.requireContext().getString(R.string.no)) { dialog, which ->
            }
            .setIcon(R.drawable.ic_warning)
            .show()
}

