package com.example.getflix.activities


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.getflix.R
import com.example.getflix.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    // static boolean variable to check the type of the user
    // can be accessed like StaticData.isVisitor, can be used in other classes
    object StaticData {
        lateinit var auth: FirebaseAuth
        var name = ""
        var isVisitor = false
        var isCustomer = false
        var isVendor = false
        var isAdmin = false
        var isGoogleUser = false
        var user: User? = null
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("780650655620-8qi5er6094ouirlb66b2c0hm6hlfo9s8.apps.googleusercontent.com")
                .requestEmail()
                .build()
        var mGoogleSignInClient: GoogleSignInClient?=null
        var account: GoogleSignInAccount ?=null
        var vendor: String? = null
        var proNum: Int? = null
        var brandNum: Int? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        StaticData.auth = Firebase.auth
        setSupportActionBar(toolbar)

    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    fun decideBottomNav(isVendor: Boolean) {
        NavigationUI.setupWithNavController(bottom_nav, navController)
        if(isVendor) {

            bottom_nav.menu.clear()
            toolbar.toolbar_title.setTextColor(ContextCompat.getColor(this, R.color.initGold))
            toolbar.setBackgroundColor(Color.BLACK)
            bottom_nav.itemBackgroundResource = R.color.black
            toolbar.btn_notification.compoundDrawableTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.initGold)))
            bottom_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.initGold)
            bottom_nav.itemTextColor = ContextCompat.getColorStateList(this, R.color.initGold)
            bottom_nav.inflateMenu(R.menu.nav_vendor_menu)
        }
        else {
            bottom_nav.menu.clear()
            toolbar.toolbar_title.setTextColor(ContextCompat.getColor(this, R.color.black))
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            bottom_nav.itemBackgroundResource = R.color.white
            toolbar.btn_notification.compoundDrawableTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary)))
            bottom_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.black)
            bottom_nav.itemTextColor = ContextCompat.getColorStateList(this, R.color.black)
            bottom_nav.inflateMenu(R.menu.nav_menu)
        }
        val appBarConfiguration = AppBarConfiguration(bottom_nav.menu)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = null
    }


}