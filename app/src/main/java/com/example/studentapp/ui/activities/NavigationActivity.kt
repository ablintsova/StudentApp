package com.example.studentapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.example.studentapp.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

const val NAV_TAG = "NavigationActivity"

class NavigationActivity : AppCompatActivity() {



    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(NAV_TAG, "onCreate: beginning")

        setContentView(R.layout.activity_navigation)
        bottomNavigation = findViewById(R.id.bottom_nav)
        setMenuItemsTextSize()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NewsFragment.newInstance())
                    .commit()
        }
        Log.d(NAV_TAG, "onCreate: middle")

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.itemNews -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, NewsFragment.newInstance())
                            .commit()
                }
                R.id.itemSchedule -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, ScheduleFragment.newInstance())
                            .commit()
                }
                R.id.itemChats -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, ChatFragment.newInstance())
                            .commit()
                }
                R.id.itemNotifications -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, NotificationFragment.newInstance())
                            .commit()
                }
                R.id.itemProfile -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, ProfileFragment.newInstance())
                            .commit()
                }
            }
            false
        }

        Log.d(NAV_TAG, "onCreate: end")
    }

    private fun setMenuItemsTextSize() {

        bottomNavigation.findViewById<View>(R.id.itemSchedule).findViewById<TextView>(R.id.largeLabel).textSize = 10f
        bottomNavigation.findViewById<View>(R.id.itemNotifications).findViewById<TextView>(R.id.largeLabel).textSize = 10f
        bottomNavigation.findViewById<View>(R.id.itemNews).findViewById<TextView>(R.id.largeLabel).textSize = 10f
        bottomNavigation.findViewById<View>(R.id.itemChats).findViewById<TextView>(R.id.largeLabel).textSize = 10f
        bottomNavigation.findViewById<View>(R.id.itemProfile).findViewById<TextView>(R.id.largeLabel).textSize = 10f

        /*var itemSchedule = bottomNavigation.findViewById<MenuItem>(R.id.itemSchedule)
        itemSchedule.*/
    }
}