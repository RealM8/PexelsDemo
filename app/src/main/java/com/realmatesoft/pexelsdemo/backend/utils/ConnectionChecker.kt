package com.realmatesoft.pexelsdemo.backend.utils

import android.content.Context
import android.net.ConnectivityManager

class ConnectionChecker (private val context: Context) {
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}