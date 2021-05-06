package com.github.zharovvv.traveler

import android.app.Application
import android.content.Context
import com.github.zharovvv.traveler.di.AppComponent
import com.github.zharovvv.traveler.di.DaggerAppComponent

class TravelerApp : Application() {

    companion object {
        private lateinit var appContext: Context

        fun getAppContext(): Context {
            return appContext
        }

        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        appComponent = DaggerAppComponent.create()
    }
}