package com.github.zharovvv.traveler.repository.network

import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkChecker
@Inject constructor(
    private val connectivityManager: ConnectivityManager
) {

    fun check(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}