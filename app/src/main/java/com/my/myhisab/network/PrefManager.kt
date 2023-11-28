package com.my.myhisab.network

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.my.myhisab.App
import com.my.myhisab.dto.LoginDto

object PrefManager {

    private var sharedPreferences: SharedPreferences? = null

    fun putString(key: String?, value: String?) {
        init()
        val editor = sharedPreferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putInt(key: String?, value: Int?) {
        init()
        val editor = sharedPreferences!!.edit()
        editor.putInt(key, value!!)
        editor.apply()
    }

    fun getString(key: String?): String? {
        init()
        return sharedPreferences!!.getString(key, "")
    }

    fun getInt(key: String?): Int {
        init()
        return sharedPreferences!!.getInt(key, 0)
    }

    fun putBoolean(key: String?, value: Boolean) {
        init()
        val editor = sharedPreferences!!.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?): Boolean {
        init()
        return sharedPreferences!!.getBoolean(key, false)
    }

    fun init(): SharedPreferences? {
        sharedPreferences = App.getInstance()?.getSharedPreferences("App", Context.MODE_PRIVATE)
        return sharedPreferences
    }

    fun saveUSer(user: LoginDto.Data) {
        sharedPreferences?.edit()?.putString("user", Gson().toJson(user))?.apply()
    }

    fun clear() {
        init()
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.commit()
    }
}