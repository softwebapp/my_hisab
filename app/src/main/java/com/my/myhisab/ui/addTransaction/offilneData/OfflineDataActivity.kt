package com.my.myhisab.ui.addTransaction.offilneData

import MySharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.my.myhisab.databinding.ActivityOfflineDataBinding
import com.my.myhisab.dto.AllTransactionDto
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseActivity
import com.my.myhisab.ui.addTransaction.OfflineTransactionDto
import com.my.utils.MultipartHelper
import com.my.utils.extensions.defaultOnNullValue
import com.my.utils.extensions.isNetworkAvailable
import com.my.utils.extensions.toast
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class OfflineDataActivity : BaseActivity() {

    var mySharedPreferences: MySharedPreferences? = null
    private var adapter: OfflineAdapter? = null
    private var courseModalArrayList: ArrayList<OfflineTransactionDto> = arrayListOf()
//    private var courseModal: OfflineTransactionDto? = null


    private lateinit var binding: ActivityOfflineDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfflineDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mySharedPreferences = MySharedPreferences(this)

        val retrievedList = mySharedPreferences?.getArrayList()
        if (retrievedList != null && retrievedList.size > 0) {
            courseModalArrayList =  retrievedList
        }else{
            toast("No Data")
        }
        buildRecyclerView()
    }

    private fun buildRecyclerView() {
        courseModalArrayList.let {
            adapter = OfflineAdapter(courseModalArrayList) { pos, modal ->
                if (isNetworkAvailable(this)){
                    courseModalArrayList.remove(modal)
                    mySharedPreferences?.removeElementById(modal.id)
                    adapter?.notifyDataSetChanged()
                    callAddtransitionApi(
                        modal.offileUniqueNumber,modal.offlineAmount,modal.offlineDate,modal.offlineRemark,modal.offlineUid
                    )
                }else{
                    toast("Please Check Your Internet Connection")
                }
            }
        }
        binding.recycler.adapter = adapter
    }


    fun callAddtransitionApi(unique: String, amount: String, date: String, remark: String, uid: String) {
        loader.show()
        val map: LinkedHashMap<String, RequestBody> = linkedMapOf<String, RequestBody>().apply {
            put("unique", MultipartHelper.prepareDataPart(unique.defaultOnNullValue()))
            put("amount", MultipartHelper.prepareDataPart(amount.defaultOnNullValue()))
            put("date", MultipartHelper.prepareDataPart(date.defaultOnNullValue()))
            put("remark", MultipartHelper.prepareDataPart(remark.defaultOnNullValue()))
            put("uid", MultipartHelper.prepareDataPart(uid.defaultOnNullValue()))
        }
        val call = RetrofitClient.apiInterface.addTransaction(map, null)
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