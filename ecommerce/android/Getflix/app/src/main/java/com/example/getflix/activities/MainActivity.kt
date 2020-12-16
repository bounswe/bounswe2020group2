package com.example.getflix.activities


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.getflix.R
import com.example.getflix.models.User
import com.example.getflix.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    // static boolean variable to check the type of the user
    // can be accessed like StaticData.isVisitor, can be used in other classes
    object StaticData {
        var name = ""
        var isVisitor = false
        var isCustomer = false
        var isVendor = false
        var isAdmin = false
        var user: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        setSupportActionBar(toolbar)
        NavigationUI.setupWithNavController(bottom_nav,navController)
        val appBarConfiguration = AppBarConfiguration(bottom_nav.menu)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar!!.title = null
       // NavigationUI.setupWithNavController(bottom_nav,navController)
       // NavigationUI.setupActionBarWithNavController(this,navController)
    // navController = navHostFragment.findNavController()
       // bottom_nav.setupWithNavController(navController)


    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }


}