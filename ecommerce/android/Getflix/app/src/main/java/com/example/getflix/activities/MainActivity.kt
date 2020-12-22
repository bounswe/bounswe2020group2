package com.example.getflix.activities


import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
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
import kotlinx.android.synthetic.main.activity_main.*


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
        var isGoogleUser = false
        var user: User? = null
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("780650655620-8qi5er6094ouirlb66b2c0hm6hlfo9s8.apps.googleusercontent.com")
                .requestEmail()
                .build()
        var mGoogleSignInClient: GoogleSignInClient?=null
        var account: GoogleSignInAccount ?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        setSupportActionBar(toolbar)
       /* val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        var account = GoogleSignIn.getLastSignedInAccount(this); */



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

    fun decideBottomNav(isVendor: Boolean) {
        NavigationUI.setupWithNavController(bottom_nav, navController)
        if(isVendor) {
            bottom_nav.menu.clear()
            bottom_nav.inflateMenu(R.menu.nav_vendor_menu)
        }
        else {
            bottom_nav.menu.clear()
            bottom_nav.inflateMenu(R.menu.nav_menu)
        }
        val appBarConfiguration = AppBarConfiguration(bottom_nav.menu)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = null
    }


}