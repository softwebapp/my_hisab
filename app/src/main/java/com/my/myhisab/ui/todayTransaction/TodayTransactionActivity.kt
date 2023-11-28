package com.my.myhisab.ui.todayTransaction

import android.os.Bundle
import com.google.gson.Gson
import com.my.myhisab.App
import com.my.myhisab.databinding.ActivityTodayTransactionBinding
import com.my.myhisab.dto.AllTransactionDto
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseActivity
import com.my.myhisab.ui.allTransaction.TodayTransactionAdapter
import com.my.utils.extensions.gone
import com.my.utils.extensions.toast
import com.my.utils.extensions.visible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodayTransactionActivity : BaseActivity() {

    var userDataAc: LoginDto.Data? = null
    private lateinit var binding: ActivityTodayTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDataAc =
            Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        callTodayTransitionApi(userDataAc?.id.toString())

    }

    fun callTodayTransitionApi(uid: String) {
        loader.show()
        val map = hashMapOf(
            "uid" to uid.trim(),
        )
        val call = RetrofitClient.apiInterface.todaytrx(map)
        call.enqueue(object : Callback<AllTransactionDto> {
            override fun onResponse(
                call: Call<AllTransactionDto?>,
                response: Response<AllTransactionDto?>
            ) {
                if (response.isSuccessful) {
                    loader.dismiss()
                    when (response.body()?.status) {
                        "success" -> {
                            response.body()?.data?.let { setUpRecycler(it) }
                        }

                        else -> {
                            App.appContext?.toast(response.body()?.message.toString())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<AllTransactionDto>, t: Throwable) {
//                loader.dismiss()
                App.appContext?.toast(t.message.toString())

            }
        })
        return
    }

    private fun setUpRecycler(list: ArrayList<AllTransactionDto.Data>) {
        if (list.isNullOrEmpty()) {
            binding.tvNoData.visible()
        } else {
            binding.tvNoData.gone()
        }
        binding.rvToday.adapter = TodayTransactionAdapter(list) {

        }
    }
}