package com.my.myhisab.ui.addTransaction

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.net.toFile
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.my.myhisab.databinding.ActivityAddTransactionBinding
import com.my.myhisab.dto.AllTransactionDto
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.dto.UniqueIdDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.network.RetrofitClient
import com.my.myhisab.ui.BaseActivity
import com.my.utils.MultipartHelper
import com.my.utils.extensions.defaultOnNullValue
import com.my.utils.extensions.gone
import com.my.utils.extensions.toast
import com.my.utils.extensions.visible
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.Calendar


class AddTransactionActivity : BaseActivity() {

    var userDataAc: LoginDto.Data? = null

    private lateinit var binding: ActivityAddTransactionBinding
    private val viewModel by viewModels<AddTranViewModel>()

    private var profileUri: File? = null
    var pictureCode = 101
    var stImages = ArrayList<MultipartBody.Part>()

    var dateFor = ""

    var isChecked = false
    var adapter: UniqueIdAdapter? = null
    var listOfUniqueID = ArrayList<UniqueIdDto.Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDataAc =
            Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSubmit.setOnClickListener {
            checkValid()
        }
        binding.tvChoose.setOnClickListener {
            ImagePicker.with(this).crop().start(pictureCode)
        }

        binding.ivImage.setOnClickListener {
            ImagePicker.with(this).crop().start(pictureCode)
        }

        binding.etDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this, { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.etDate.setText(dat)
                    dateFor =
                        year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                },
                year, month, day
            )
            datePickerDialog.show()
        }


        binding.etUniqueNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(etText: Editable?) {
                if (etText!!.count() > 3) {
                    if (isChecked) {
                        binding.recycler.gone()
                    } else {
                        viewModel.callUniqueIDApi(userDataAc?.id.toString(), etText.toString())
                    }
                }
            }
        })

        viewModel.response.observe(this) {
            listOfUniqueID = it.data
            setUpAdapter(it.data)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUpAdapter(data: ArrayList<UniqueIdDto.Data>) {
        binding.recycler.visible()
        adapter = UniqueIdAdapter(data) {
            binding.apply {
                isChecked = true
                etUniqueNo.setText(it.value.toString().defaultOnNullValue())
                consDetail.visible()
                recycler.gone()
                tvPassbookId.text = "Passbook ID: " + it.value.defaultOnNullValue()
                tvName.text = "Name: " + it.name.defaultOnNullValue()
                tvContact.text = "Contact No.: " + it.contactnumber.defaultOnNullValue()
                tvEmail.text = "Email-id: " + it.email.defaultOnNullValue()
                tvStartDate.text = "Start Date: " + it.startDate.defaultOnNullValue()
                tvendDate.text = "End Date: " + it.endDate.defaultOnNullValue()
                tvAmount.text = "Amount: " + it.perMonth.defaultOnNullValue()
                tvLastTransAmount.text =
                    "Last Transaction Amount: " + it.trnAmount.defaultOnNullValue()
                isChecked = false
            }
        }
        binding.recycler.adapter = adapter

    }

    private fun checkValid() {
        if (binding.etUniqueNo.text.isNullOrEmpty()) {
            toast("Please Enter Unique No")
        } else if (binding.etDate.text.isNullOrEmpty()) {
            toast("Please Enter Date")
        } else if (binding.etAmount.text.isNullOrEmpty()) {
            toast("Please Enter Amount")
        } else if (binding.etRemark.text.isNullOrEmpty()) {
            toast("Please Enter Remark")
        } else {
            callAddtransitionApi(
                binding.etUniqueNo.text.toString(), binding.etAmount.text.toString(), dateFor,
                binding.etRemark.text.toString(), userDataAc?.id.toString(),
            )
        }
    }

    fun callAddtransitionApi(
        unique: String,
        amount: String,
        date: String,
        remark: String,
        uid: String
    ) {
        loader.show()
        val map: LinkedHashMap<String, RequestBody> = linkedMapOf<String, RequestBody>().apply {
            put("unique", MultipartHelper.prepareDataPart(unique.defaultOnNullValue()))
            put("amount", MultipartHelper.prepareDataPart(amount.defaultOnNullValue()))
            put("date", MultipartHelper.prepareDataPart(date.defaultOnNullValue()))
            put("remark", MultipartHelper.prepareDataPart(remark.defaultOnNullValue()))
            put("uid", MultipartHelper.prepareDataPart(uid.defaultOnNullValue()))
        }
        val call = RetrofitClient.apiInterface.addTransaction(map, stImages)
        call.enqueue(object : Callback<AllTransactionDto> {
            override fun onResponse(
                call: Call<AllTransactionDto?>,
                response: Response<AllTransactionDto?>
            ) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                when (requestCode) {
                    pictureCode -> {
                        profileUri = uri.toFile()
//                        binding.ivImage.setLocalImage(uri)
                        Glide.with(this).load(uri).into(binding.ivImage)
                        stImages.add(
                            MultipartHelper.prepareFilePart(
                                this,
                                "name",
                                Uri.fromFile(profileUri),
                                profileUri!!
                            )
                        )
                    }
                }
            }

            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }



}