package com.my.myhisab.ui.fragment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.my.myhisab.App.Companion.appContext
import com.my.myhisab.dto.DashboardDto
import com.my.myhisab.dto.UniqueIdDto
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseViewModel
import com.my.utils.extensions.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : BaseViewModel() {



    private val _response: MutableLiveData<DashboardDto> = MutableLiveData()
    val response: LiveData<DashboardDto> = _response

    fun callDashboardApi(uid :String) : MutableLiveData<DashboardDto> {
//       loader.show()
        val map = hashMapOf(
            "uid" to uid.trim(),
        )

        val call = RetrofitClient.apiInterface.dashboard(map)
        call.enqueue(object : Callback<DashboardDto> {
            override fun onResponse(call: Call<DashboardDto?>, response: Response<DashboardDto?>) {
                if (response.isSuccessful) {
//                    loader.dismiss()
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

            override fun onFailure(call: Call<DashboardDto>, t: Throwable) {
                loader.dismiss()
//                appContext?.toast(t.message.toString())
            }
        })
        return _response
    }


    // offline List Saved

    private val _responseOffline: MutableLiveData<UniqueIdDto> = MutableLiveData()
    val responseOffline: LiveData<UniqueIdDto> = _responseOffline

    fun callOfflineUniqueApi(uid :String) : MutableLiveData<UniqueIdDto> {
//        loader.show()
        val map = hashMapOf(
            "id" to uid,
        )
        val call = RetrofitClient.apiInterface.searchnameoffline(map)
       call.enqueue(object : Callback<UniqueIdDto?> {
           override fun onResponse(call: Call<UniqueIdDto?>, response: Response<UniqueIdDto?>) {
//               loader.dismiss()
               if (response.isSuccessful) {
                   when (response.body()?.status) {
                       "success" -> {
                           _responseOffline.value = response.body()
                       }
                       else -> {
                           appContext?.toast(response.body()?.message.toString())
                       }
                   }
               }
           }

           override fun onFailure(call: Call<UniqueIdDto?>, t: Throwable) {
//               loader.dismiss()
//               appContext?.toast(t.message.toString())
           }
       })
        return _responseOffline
    }

}