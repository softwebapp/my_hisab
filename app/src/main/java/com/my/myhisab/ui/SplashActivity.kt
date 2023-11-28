package com.my.myhisab.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.my.myhisab.databinding.ActivitySplashBinding
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.ui.login.LoginActivity
import com.my.utils.extensions.openActivity
import com.my.utils.extensions.toast
import com.my.utils.extensions.transparentStatusBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    var userDataAc :LoginDto.Data?=null
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentStatusBar()
        userDataAc =  Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)


        lifecycleScope.launch {
            delay(2000)
            if (userDataAc?.id.isNullOrEmpty()) {
                openActivity<LoginActivity>()
                finish()
            } else {
                openActivity<MainActivity>()
                finish()
            }

        }

    }

}