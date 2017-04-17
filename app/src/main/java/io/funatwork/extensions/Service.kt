package io.funatwork.extensions

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by mmaillot on 4/16/17.
 */
fun Context.getConnectivityManager() =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager