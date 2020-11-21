package com.example.getflix.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.getflix.R
import com.example.getflix.ui.fragment.CartFragment
import com.example.getflix.ui.fragment.CategoriesFragment
import com.example.getflix.ui.fragment.HomePageFragment
import com.example.getflix.ui.fragment.ProfileFragment


import com.example.getflix.ui.fragment.*


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // static boolean variable to check the type of the user
    // can be accessed like StaticData.isVisitor, can be used in other classes
    object StaticData {
        var isVisitor = false
        var isCustomer = false
        var isVendor = false
        var isAdmin = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homePageFragment = HomePageFragment()
        val profileFragment = ProfileFragment()
        val categoriesFragment = CategoriesFragment()
        val cartFragment = CartFragment()
        val favoritesFragment = FavoritesFragment()

        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homePageFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
                R.id.ic_categories -> makeCurrentFragment(categoriesFragment)
                R.id.ic_cart -> makeCurrentFragment(cartFragment)
                R.id.ic_favorites -> makeCurrentFragment(favoritesFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
       supportFragmentManager.beginTransaction().apply {
           replace(R.id.my_nav_host_fragment,fragment)
           commit()
       }
    }
}