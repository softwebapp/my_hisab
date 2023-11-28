package com.my.myhisab.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import com.google.gson.Gson
import com.my.myhisab.databinding.ActivityLoginBinding
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.ui.BaseActivity
import com.my.myhisab.ui.MainActivity
import com.my.utils.extensions.isNetworkAvailable
import com.my.utils.extensions.openActivity
import com.my.utils.extensions.toast

class LoginActivity : BaseActivity() {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.loading.observe(this) {
            when (it) {
                true -> {
                    loader.show()
                }
                false -> {
                    loader.dismiss()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            loginValid()
        }

        viewModel.response.observe(this) {
            PrefManager.putString(ApiDetails.LogIn, Gson().toJson(it.data))
//            it.data?.let { it1 -> PrefManager.saveUSer(it1) }
            openActivity<MainActivity>()
        }

        if (isNetworkAvailable(this)) {
//            toast("true")
        } else {
//            toast("false")
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun loginValid() {
        if (binding.etUserName.text.isNullOrEmpty()) {
            toast("Please enter UserId")
        } else if (binding.etPassword.text.isNullOrEmpty()) {
            toast("Please enter password")
        } else {
            viewModel.callLoginApi(
                binding.etUserName.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }


}