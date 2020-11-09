package com.example.getflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.getflix.ui.fragment.CartFragment
import com.example.getflix.ui.fragment.CategoriesFragment
import com.example.getflix.ui.fragment.HomePageFragment
import com.example.getflix.ui.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homePageFragment = HomePageFragment()
        val profileFragment = ProfileFragment()
        val categoriesFragment = CategoriesFragment()
        val cartFragment = CartFragment()

        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homePageFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
                R.id.ic_categories -> makeCurrentFragment(categoriesFragment)
                R.id.ic_cart -> makeCurrentFragment(cartFragment)
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