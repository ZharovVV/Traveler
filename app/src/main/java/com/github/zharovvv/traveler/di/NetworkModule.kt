package com.github.zharovvv.traveler.di

import android.content.Context
import android.net.ConnectivityManager
import com.github.zharovvv.traveler.TravelerApp
import com.github.zharovvv.traveler.repository.network.CacheProvider
import com.github.zharovvv.traveler.repository.network.Interceptors
import com.github.zharovvv.traveler.repository.network.TravelerApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideTravelerApiService(
        @Named("cacheControlHttpClient")
        httpClient: OkHttpClient
    ): TravelerApiService {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/traveler")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(TravelerApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("cacheControlHttpClient")
    fun provideCacheControlHttpClient(
        cacheProvider: CacheProvider,
        cacheInterceptor: Interceptors.CacheInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cacheProvider.provideCache())
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(cacheInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("simpleHttpClient")
    fun provideSimpleHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("cacheDir")
    fun provideCacheDir(): File {
        return TravelerApp.appContext.cacheDir
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(): ConnectivityManager {
        return TravelerApp.appContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}