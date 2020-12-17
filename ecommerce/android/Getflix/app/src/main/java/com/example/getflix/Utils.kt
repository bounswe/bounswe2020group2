package com.example.getflix

import android.content.DialogInterface
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.getflix.activities.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

fun infoAlert(fragment: Fragment, message: String) {
    MaterialAlertDialogBuilder(fragment.requireContext(), R.style.MaterialAlertDialog_color)
            .setTitle("Info")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, which ->
            }
            .setIcon(R.drawable.ic_info)
            .show()
}

fun doneAlert(fragment: Fragment, message: String) {
    MaterialAlertDialogBuilder(fragment.requireContext(), R.style.MaterialAlertDialog_color)
            .setTitle("Success")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, which ->
            }
            .setIcon(R.drawable.ic_check)
            .show()
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
