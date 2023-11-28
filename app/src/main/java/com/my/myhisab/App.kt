package com.my.myhisab

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.my.myhisab.network.PrefManager


class App : Application() {

    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        @SuppressLint("StaticFieldLeak")
        var  appContext :Context? = null

        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App? {
            if (instance == null) {
                instance = App()
            }
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = this
        sharedPreferences= PrefManager.init()!!
    }

    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
    }
}

