package com.my.myhisab.ui

import android.os.Bundle
import com.google.gson.Gson
import com.my.myhisab.App
import com.my.myhisab.databinding.ActivityChangePasswordBinding
import com.my.myhisab.dto.AllTransactionDto
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.network.RetrofitClient
import com.my.utils.extensions.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : BaseActivity() {
    var userDataAc : LoginDto.Data?=null

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDataAc =  Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)

        binding.ivBack.setOnClickListener {
        onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSubmit.setOnClickListener {
            checkValid()
        }
    }

    private fun checkValid() {
        if (binding.etOldPass.text.isNullOrEmpty()) {
            toast("Please Enter old password")
        } else if (binding.etNewPassword.text.isNullOrEmpty()) {
            toast("Please Enter new password")
        } else {
            callalltransitionApi(userDataAc?.id.toString(),binding.etOldPass.text.toString(),binding.etNewPassword.text.toString())
        }
    }

    fun callalltransitionApi(uid :String,oldpwd:String,newpwd:String) {
        loader.show()
        val map = hashMapOf(
            "userid" to uid.trim(),
            "oldpwd" to oldpwd.trim(),
            "newpwd" to newpwd.trim(),
        )
        val call = RetrofitClient.apiInterface.changePass(map)
        call.enqueue(object : Callback<AllTransactionDto> {
            override fun onResponse(call: Call<AllTransactionDto?>, response: Response<AllTransactionDto?>) {
                if (response.isSuccessful) {
                    loader.dismiss()
                    when (response.body()?.status) {
                        "success" -> {
                            toast(response.body()?.message.toString())
                            onBackPressedDispatcher.onBackPressed()
                        }
                        else -> {
                            toast(response.body()?.message.toString())
                        }
                    }
                }
            }
            override fun onFailure(call: Call<AllTransactionDto>, t: Throwable) {
                loader.dismiss()
               toast(t.message.toString())

            }
        })
        return
    }

}