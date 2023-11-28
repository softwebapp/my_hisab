package com.my.myhisab.ui.customerList

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson
import com.my.myhisab.App
import com.my.myhisab.databinding.ActivityCustomerListBinding
import com.my.myhisab.dto.CustomerListDto
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseActivity
import com.my.myhisab.ui.transactionHistory.TransactionHistoryActivity
import com.my.utils.extensions.openActivity
import com.my.utils.extensions.snack
import com.my.utils.extensions.snackx
import com.my.utils.extensions.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class CustomerListActivity : BaseActivity() {

    private var adapter: CustomerAdapter? = null
    private var list: ArrayList<CustomerListDto.Data>? = null

    var userDataAc : LoginDto.Data?=null

    private lateinit var binding: ActivityCustomerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDataAc =  Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        callDashboardApi(userDataAc?.id.toString())

        binding.etSearch.doAfterTextChanged {
            filter(it.toString())
        }
    }


    fun callDashboardApi(uid :String) {
       loader.show()
        val map = hashMapOf(
            "uid" to uid.trim(),
        )
        val call = RetrofitClient.apiInterface.customerlist(map)
        call.enqueue(object : Callback<CustomerListDto> {
            override fun onResponse(call: Call<CustomerListDto?>, response: Response<CustomerListDto?>) {
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
            override fun onFailure(call: Call<CustomerListDto>, t: Throwable) {
//                loader.dismiss()
                App.appContext?.toast(t.message.toString())

            }
        })
        return 
    }

    private fun setUpRecycler(data: ArrayList<CustomerListDto.Data>) {
//        showProgressBar()
        list = data
       adapter = CustomerAdapter(data){
            openActivity<TransactionHistoryActivity>("uniqeId" to it.uniqeId.toString(),"name" to it.name.toString())
        }
        binding.rvCustomerList.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.rvCustomerList.snackx("loading please wait.."){}
                    binding.rvCustomerList.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        binding.rvCustomerList.adapter = adapter
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<CustomerListDto.Data>()
        list?.forEach {
            if (it.name?.toLowerCase()!!.contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(it)
            }
        }
        if (filteredList.isEmpty()) {
            toast("No Data Found..")
        } else {
            adapter?.filterList(filteredList)
        }
    }


}