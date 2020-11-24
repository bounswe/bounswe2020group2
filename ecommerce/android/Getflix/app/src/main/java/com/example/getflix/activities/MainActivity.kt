package com.example.getflix.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.getflix.R
import com.example.getflix.ui.fragments.CartFragment
import com.example.getflix.ui.fragments.CategoriesFragment
import com.example.getflix.ui.fragments.HomePageFragment
import com.example.getflix.ui.fragments.ProfileFragment


import com.example.getflix.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // static boolean variable to check the type of the user
    // can be accessed like StaticData.isVisitor, can be used in other classes
    object StaticData {
        var name = ""
        var sproducts = null
        val scategories = null
        const val BASE_URL = "http://10.0.2.2:8000/"
        var isVisitor = false
        var isCustomer = false
        var isVendor = false
        var isAdmin = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


           /* val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)
            val navController = findNavController(R.id.my_nav_host_fragment)
            setupActionBarWithNavController(navController) */


        /*val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(R.id.my_nav_host_fragment)
        bottomNavView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homePageFragment,
        R.id.categoriesFragment, R.id.favoritesFragment,R.id.cartFragment,
        R.id.profileFragment)) */
        //this.setupActionBarWithNavController(navController,appBarConfiguration)

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

    fun makeCurrentFragment(fragment: Fragment) {
       supportFragmentManager.beginTransaction().apply {
           replace(R.id.my_nav_host_fragment,fragment)
           commit()
       }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}