package com.example.studentapp.application

import android.app.Application

val prefs: Preferences by lazy {
    App.prefs!!
}

class App: Application()
{
    companion object {
        var prefs: Preferences? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Preferences(applicationContext)
    }
}