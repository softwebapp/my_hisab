package com.my.myhisab.ui

import MySharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.my.myhisab.App
import com.my.myhisab.R
import com.my.myhisab.databinding.ActivityMainBinding
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.dto.UniqueIdDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.fragment.HomeFragment
import com.my.myhisab.ui.fragment.UserFragment
import com.my.utils.extensions.defaultOnNullValue
import com.my.utils.extensions.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    var userDataAc : LoginDto.Data?=null
    var sharedPreference: MySharedPreferences? = null

    private var doubleBackToExitPressedOnce = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())
        userDataAc =  Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)
        sharedPreference = MySharedPreferences(this)
        callOfflineUniqueApi(userDataAc?.id.toString().defaultOnNullValue())
//        loader.show()

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.user -> {
                    loadFragment(UserFragment())
                    true
                }

                else -> {
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce){
            super.onBackPressed()
            finishAffinity()
        }
        doubleBackToExitPressedOnce = true
        toast("Please click BACK again to exit")
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun callOfflineUniqueApi(uid :String) {
        val map = hashMapOf(
            "id" to uid,
        )
        val call = RetrofitClient.apiInterface.searchnameoffline(map)
        call.enqueue(object : Callback<UniqueIdDto?> {
            override fun onResponse(call: Call<UniqueIdDto?>, response: Response<UniqueIdDto?>) {
                if (response.isSuccessful) {
                    when (response.body()?.status) {
                        "success" -> {
                            response.body()?.data?.let {
                                sharedPreference?.saveOfflineUniqueArrayList(it)
                            }
                        }
                        else -> {
                            App.appContext?.toast(response.body()?.message.toString())
                        }
                    }
                }
            }
            override fun onFailure(call: Call<UniqueIdDto?>, t: Throwable) {}
        })
        return
    }

}