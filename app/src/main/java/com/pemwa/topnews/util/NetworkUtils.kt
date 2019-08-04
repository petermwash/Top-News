package com.pemwa.topnews.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import com.pemwa.topnews.AppContext
import com.pemwa.topnews.NewsApplication.Companion.isNetworkConnected
import com.pemwa.topnews.R
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityStatus : LiveData<Boolean>() {

    private val connectivityManager = AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) {
            super.onLost(network)
            postValue(false)
        }

        override fun onAvailable(network: Network?) {
            super.onAvailable(network)
            postValue(true)
        }
    }

    override fun onInactive() {
        super.onInactive()

        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onActive() {
        super.onActive()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }
    }
}

class ConnectivityInterceptor(context: Context) : Interceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkConnected) {

            throw NoConnectivityException(appContext.resources.getString(R.string.check_network_connectivity))
        }
        return chain.proceed(chain.request())
    }
}

class NoConnectivityException(message: String) : Throwable(message)

fun isNetworkConnected(): Boolean = isNetworkConnected
