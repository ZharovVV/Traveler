package com.github.zharovvv.traveler.repository.network

import okhttp3.Cache
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CacheProvider
@Inject constructor(
    @field:Named("cacheDir")
    private val cacheDir: File
) {

    companion object {
        private const val CACHE_SIZE: Long = 5 * 1024 * 1024 //5 Мб
    }

    fun provideCache(): Cache {
        return Cache(cacheDir, CACHE_SIZE)
    }
}