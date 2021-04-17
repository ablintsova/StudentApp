package com.example.studentapp.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.example.studentapp.presenter.NavigationPresenter
import com.example.studentapp.presenter.NavigationPresenterInterface
import com.example.studentapp.view.fragments.NewsFragment
import com.example.studentapp.view.fragments.ProfileFragment
import com.example.studentapp.view.fragments.ScheduleFragment
import com.example.studentapp.view.interfaces.NavigationViewInterface
import com.google.android.material.bottomnavigation.BottomNavigationView

const val NAV_TAG = "NavigationActivity"

class NavigationActivity : AppCompatActivity(), NavigationViewInterface {

    lateinit var bottomNavigation: BottomNavigationView
    private lateinit var presenter: NavigationPresenterInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(NAV_TAG, "onCreate: beginning")

        presenter = NavigationPresenter(this)
        presenter.getApiData()

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

    override fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}