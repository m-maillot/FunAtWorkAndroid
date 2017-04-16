package io.funatwork.core.net

import android.net.ConnectivityManager

class ConnectionUtils(val connectivityManager: ConnectivityManager) {
    fun isThereInternetConnection() = connectivityManager.activeNetworkInfo.isConnectedOrConnecting
}