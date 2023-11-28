package com.my.myhisab.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(private val mContext: Context?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    val isConnected: Boolean
        get() {
            val connectivityManager = mContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }


//    private inner class NoConnectivityException : Throwable()

}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
    // You can send any message whatever you want from here.
}
