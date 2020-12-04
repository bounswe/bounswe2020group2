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
import androidx.navigation.ui.setupWithNavController
import com.example.getflix.R
import com.example.getflix.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var nav = false

    // static boolean variable to check the type of the user
    // can be accessed like StaticData.isVisitor, can be used in other classes
    object StaticData {
        var name = ""
        var sproducts = null
        val scategories = null
        const val BASE_URL = "http://ec2-18-189-28-20.us-east-2.compute.amazonaws.com:8000/"
        var isVisitor = false
        var isCustomer = false
        var isVendor = false
        var isAdmin = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        //setSupportActionBar(this.toolbar)
        //setupActionBarWithNavController(navController)
        bottom_nav.setupWithNavController(navController)


        /*val appBarConfiguration = AppBarConfiguration(navController.graph)

        toolbar.setupWithNavController(navController, appBarConfiguration)*/
        //supportActionBar!!.setDisplayHomeAsUpEnabled(false)


    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.my_nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

     fun setApplicationLocale(locale: String) {
        val resources = resources
        val dm: DisplayMetrics = resources.displayMetrics
        val config = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(Locale(locale.toLowerCase()))
        } else {
            config.locale = Locale(locale.toLowerCase())
        }
        resources.updateConfiguration(config, dm)
    }



}