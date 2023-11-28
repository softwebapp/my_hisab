package com.my.myhisab.ui.transactionHistory


import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.my.myhisab.App.Companion.appContext
import com.my.myhisab.dto.LedgerDto
import com.my.myhisab.dto.TransctionAllHistoryDto
import com.my.myhisab.dto.UserHisteryDataDto
import com.my.myhisab.network.NoConnectivityException
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseViewModel
import com.my.utils.extensions.defaultOnNullValue
import com.my.utils.extensions.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TranscationHitoryViewModel : BaseViewModel() {

    val uid = ObservableField("")
    val uniqueid = ObservableField("")

    private val _response: MutableLiveData<TransctionAllHistoryDto> = MutableLiveData()
    val response: LiveData<TransctionAllHistoryDto> = _response

    fun callCustomerTransactionApi() : MutableLiveData<TransctionAllHistoryDto> {
//       loader.show()
        val map = hashMapOf(
            "uid" to uid.get().toString().defaultOnNullValue(),
            "uniqueid" to uniqueid.get().toString().defaultOnNullValue()
        )
        val call = RetrofitClient.apiInterface.customertransaction(map)
        call.enqueue(object : Callback<TransctionAllHistoryDto> {
            override fun onResponse(call: Call<TransctionAllHistoryDto?>, response: Response<TransctionAllHistoryDto?>) {
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

            override fun onFailure(call: Call<TransctionAllHistoryDto>, t: Throwable) {
//                loader.dismiss()
                appContext?.toast(t.message.toString())

            }
        })
        return _response
    }

    fun customerpostofficedeposite() : MutableLiveData<TransctionAllHistoryDto> {
//       loader.show()
        val map = hashMapOf(
            "uid" to uid.get().toString().defaultOnNullValue(),
            "uniqueid" to uniqueid.get().toString().defaultOnNullValue()
        )
        val call = RetrofitClient.apiInterface.customerpostofficedeposite(map)
        call.enqueue(object : Callback<TransctionAllHistoryDto> {
            override fun onResponse(call: Call<TransctionAllHistoryDto?>, response: Response<TransctionAllHistoryDto?>) {
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

            override fun onFailure(call: Call<TransctionAllHistoryDto>, t: Throwable) {
//                loader.dismiss()
                appContext?.toast(t.message.toString())

            }
        })
        return _response
    }

    private val _responseLedger: MutableLiveData<LedgerDto> = MutableLiveData()
    val responseLedger: LiveData<LedgerDto> = _responseLedger

    fun customerledger() : MutableLiveData<LedgerDto> {
        _loading.value = true
        val map = hashMapOf(
            "uid" to uid.get().toString().defaultOnNullValue(),
            "uniqueid" to uniqueid.get().toString().defaultOnNullValue()
        )
        val call = RetrofitClient.apiInterface.customerledger(map)
        call.enqueue(object : Callback<LedgerDto> {
            override fun onResponse(call: Call<LedgerDto?>, response: Response<LedgerDto?>) {
                _loading.value = false
                if (response.isSuccessful) {
                    when (response.body()?.status) {
                        "success" -> {
                            _responseLedger.value = response.body()
                        }
                        else -> {
                            appContext?.toast(response.body()?.message.toString())
                        }
                    }
                }

            }

            override fun onFailure(call: Call<LedgerDto>, t: Throwable) {
                _loading.value = false
                appContext?.toast(t.message.toString())

            }
        })
        return _responseLedger
    }

    private val _responseHistory: MutableLiveData<UserHisteryDataDto> = MutableLiveData()
    val responseHistory: LiveData<UserHisteryDataDto> = _responseHistory

    fun customertransactioncalc() : MutableLiveData<UserHisteryDataDto> {
        _loading.value = true
        val map = hashMapOf(
            "uid" to uid.get().toString().defaultOnNullValue(),
            "uniqueid" to uniqueid.get().toString().defaultOnNullValue()
        )
        val call = RetrofitClient.apiInterface.customertransactioncalc(map)
        call.enqueue(object : Callback<UserHisteryDataDto> {
            override fun onResponse(call: Call<UserHisteryDataDto?>, response: Response<UserHisteryDataDto?>) {
                _loading.value = false
                if (response.isSuccessful) {
                    when (response.body()?.status) {
                        "success" -> {
                            _responseHistory.value = response.body()
                        }
                        else -> {
                            appContext?.toast(response.body()?.message.toString())
                        }
                    }
                }

            }

            override fun onFailure(call: Call<UserHisteryDataDto>, t: Throwable) {
                _loading.value = false
                appContext?.toast(t.message.toString())

            }
        })
        return _responseHistory
    }
}