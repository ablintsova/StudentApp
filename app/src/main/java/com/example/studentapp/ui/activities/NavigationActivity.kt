package com.example.studentapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.example.studentapp.model.Constants
import com.example.studentapp.model.Network
import com.example.studentapp.model.api.TspuApi
import com.example.studentapp.model.api.groups.GroupsCallback
import com.example.studentapp.model.api.login.LoginCallback
import com.example.studentapp.model.api.timetable.TimetableCallback
import com.example.studentapp.ui.fragments.NewsFragment
import com.example.studentapp.ui.fragments.ProfileFragment
import com.example.studentapp.ui.fragments.ScheduleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

const val NAV_TAG = "NavigationActivity"

class NavigationActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
    private lateinit var tspuApi: TspuApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(NAV_TAG, "onCreate: beginning")

        tspuApi = Network().getRetrofitClient(Constants.TIMETABLE_URL).create(TspuApi::class.java)

        setContentView(R.layout.activity_navigation)
        bottomNavigation = findViewById(R.id.bottom_nav)
        setMenuItemsTextSize()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewsFragment.newInstance(), "newsFragmentTag")
                .commit()
        }
        Log.d(NAV_TAG, "onCreate: middle")

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.itemNews -> {
                    Log.d(NAV_TAG, "onNews: ready to show news")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, NewsFragment.newInstance(),"newsFragmentTag")
                        .commit()
                }
                R.id.itemSchedule -> {
                    Log.d(NAV_TAG, "onSchedule: ready to get groups")
                    tspuApi.getGroups().enqueue(GroupsCallback(this))
                    Log.d(NAV_TAG, "onSchedule: ready to get timetable")
                    tspuApi.getTimetable("493").enqueue(TimetableCallback(this))
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ScheduleFragment.newInstance())
                        .commit()
                }
                R.id.itemProfile -> {
                    Log.d(NAV_TAG, "onProfile: ready to show profile")
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

        bottomNavigation.findViewById<View>(R.id.itemSchedule)
            .findViewById<TextView>(R.id.largeLabel).textSize = 10f
        bottomNavigation.findViewById<View>(R.id.itemNews)
            .findViewById<TextView>(R.id.largeLabel).textSize = 10f
        bottomNavigation.findViewById<View>(R.id.itemProfile)
            .findViewById<TextView>(R.id.largeLabel).textSize = 10f
    }
    override fun onBackPressed() {
        val fragment: NewsFragment? = supportFragmentManager
            .findFragmentByTag("newsFragmentTag") as NewsFragment?
        fragment?.let {
            it.onBackPressed()
        }
    }
}