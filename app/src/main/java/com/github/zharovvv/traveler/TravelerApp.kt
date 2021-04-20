package com.github.zharovvv.traveler

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.github.zharovvv.traveler.di.AppComponent
import com.github.zharovvv.traveler.di.DaggerAppComponent
import com.github.zharovvv.traveler.repository.database.TravelerDatabase
import com.github.zharovvv.traveler.repository.network.CacheProvider
import com.github.zharovvv.traveler.repository.network.Interceptors
import com.github.zharovvv.traveler.repository.network.NetworkChecker
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TravelerApp : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        appComponent = DaggerAppComponent.create()
    }
}