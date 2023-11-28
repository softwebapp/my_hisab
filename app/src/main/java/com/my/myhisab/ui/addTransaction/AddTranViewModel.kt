package com.my.myhisab.ui.addTransaction


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.my.myhisab.App.Companion.appContext
import com.my.myhisab.dto.UniqueIdDto
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseViewModel
import com.my.utils.extensions.defaultOnNullValue
import com.my.utils.extensions.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddTranViewModel : BaseViewModel() {

    private val _response: MutableLiveData<UniqueIdDto> = MutableLiveData()
    val response: LiveData<UniqueIdDto> = _response

    fun callUniqueIDApi(id:String,term:String) : MutableLiveData<UniqueIdDto> {
        val map = hashMapOf(
            "id" to id.defaultOnNullValue(),
            "term" to term.trim().defaultOnNullValue()
        )

        val call = RetrofitClient.apiInterface.uniuqeId(map)
        call.enqueue(object : Callback<UniqueIdDto> {
            override fun onResponse(call: Call<UniqueIdDto?>, response: Response<UniqueIdDto?>) {
                if (response.isSuccessful) {
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

            override fun onFailure(call: Call<UniqueIdDto>, t: Throwable) {
                appContext?.toast(t.message.toString())

            }
        })
        return _response
    }



}