package com.my.myhisab.network

import com.my.myhisab.App.Companion.appContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitClient {

    var okHttpClient: OkHttpClient? = null
    val retrofitClient: Retrofit.Builder by lazy {
        if (okHttpClient == null) {
            initOkHttp()
        }
        Retrofit.Builder()
            .baseUrl(ApiDetails.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptor)
//        httpClient.addInterceptor(NetworkConnectionInterceptor(appContext))
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        okHttpClient = httpClient.build()
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }

}