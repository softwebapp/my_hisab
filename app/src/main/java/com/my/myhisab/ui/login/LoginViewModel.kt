package com.my.myhisab.ui.login


import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.my.myhisab.App.Companion.appContext
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.network.NoConnectivityException
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseViewModel
import com.my.utils.extensions.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : BaseViewModel() {

//    val stMobileNumber = ObservableField("")
//    val stPassword = ObservableField("")

    private val _response: MutableLiveData<LoginDto> = MutableLiveData()
    val response: LiveData<LoginDto> = _response

    fun callLoginApi(username :String,password:String) : MutableLiveData<LoginDto> {
        _loading.value = true
        val map = hashMapOf(
            "username" to username.trim(),
            "password" to password.trim()
        )

        val call = RetrofitClient.apiInterface.loginUser(map)
        call.enqueue(object : Callback<LoginDto> {
            override fun onResponse(call: Call<LoginDto?>, response: Response<LoginDto?>) {
                if (response.isSuccessful) {
                    _loading.value = false
                    loader.dismiss()
                    when (response.body()?.status) {
                        "success" -> {
                            _response.value = response.body()
                        }
                        else -> {
                            appContext?.toast(response.body()?.message.toString())
                        }
                    }
                }

            }

            override fun onFailure(call: Call<LoginDto>, t: Throwable) {
                _loading.value = true
                appContext?.toast(t.message.toString())

            }
        })
        return _response
    }

}