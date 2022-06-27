package com.example.foodapp.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.foodapp.R
import com.example.foodapp.fragment.CatogriesFragment
import com.example.foodapp.fragment.FavoritesFragment
import com.example.foodapp.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {
    var favoritesFragment = FavoritesFragment()
    var catogriesFragment = CatogriesFragment()
    private val navigasjonen=    BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.homeFragment -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.favoritesFragment -> {

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment, favoritesFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.categoryFragment -> {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment, catogriesFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
                }

            }

true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(navigasjonen)



    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
            commit()


        }
}