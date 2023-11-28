package com.my.myhisab.ui.allTransaction

import android.app.DatePickerDialog
import android.os.Bundle
import com.google.gson.Gson
import com.my.myhisab.App
import com.my.myhisab.databinding.ActivityAllTransactionListBinding
import com.my.myhisab.dto.AllTransactionDto
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseActivity
import com.my.utils.extensions.gone
import com.my.utils.extensions.toast
import com.my.utils.extensions.visible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class AllTransactionListActivity : BaseActivity() {

    var formDate = ""
    var toDate = ""
    var userDataAc: LoginDto.Data? = null
    private lateinit var binding: ActivityAllTransactionListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllTransactionListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDataAc =
            Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.etFormDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                binding.etFormDate.setText(dat)
                formDate =   year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        binding.etToDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                binding.etToDate.setText(dat)
                toDate = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
            },
                year, month, day
            )
            datePickerDialog.show()
        }


        binding.btnSubmit.setOnClickListener {
            if (binding.etFormDate.text.isNullOrEmpty()){
                toast("Please select start date")
            }else if (binding.etToDate.text.isNullOrEmpty()){
                toast("Please select end date")
            }
            else{
                callalltransitionApi(userDataAc?.id.toString(),formDate,toDate)
            }
        }



    }

    fun callalltransitionApi(uid :String,from_date:String,toDate:String) {
        loader.show()
        val map = hashMapOf(
            "uid" to uid.trim(),
            "from_date" to from_date.trim(),
            "to_date" to toDate.trim(),
        )
        val call = RetrofitClient.apiInterface.alltransition(map)
        call.enqueue(object : Callback<AllTransactionDto> {
            override fun onResponse(call: Call<AllTransactionDto?>, response: Response<AllTransactionDto?>) {
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
                loader.dismiss()
                App.appContext?.toast(t.message.toString())

            }
        })
        return
    }

    private fun setUpRecycler(list: ArrayList<AllTransactionDto.Data>) {
        if (list.isNullOrEmpty()){
            binding.tvNoData.visible()
        }else{
            binding.tvNoData.gone()
        }
        binding.recycler.adapter = TodayTransactionAdapter(list){

        }
    }
}