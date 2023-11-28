package com.my.myhisab.ui

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.utils.ProgressView
import com.my.utils.extensions.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseActivity : AppCompatActivity() {


    val loader by lazy {
        ProgressView.getLoader(this)
    }


    val loaderRecycler by lazy {
        ProgressView.getLoader(this)
    }

    var userData : LoginDto.Data?=null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                getUser()
            }
        }
    }

    fun message(msg :String){
        toast(msg)
    }

    fun showProgressBar() {
        loaderRecycler.show()
    }

    fun hideProgressBar() {
        loaderRecycler.hide()
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getUser(): LoginDto.Data? {
        return try {
            Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

  /*  fun getUserData(): LoginDto.Data? {
        val data = PrefManager.getString("user") ?: return null
        return Gson().fromJson(data, LoginDto.Data::class.java)
    }*/


}