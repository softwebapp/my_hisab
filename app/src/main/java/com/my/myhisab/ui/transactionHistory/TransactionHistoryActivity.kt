package com.my.myhisab.ui.transactionHistory

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.my.myhisab.databinding.ActivityTransactionHistoryBinding
import com.my.myhisab.dto.CustomerListDto
import com.my.myhisab.dto.LedgerDto
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.dto.TransctionAllHistoryDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.PrefManager
import com.my.myhisab.ui.BaseActivity
import com.my.myhisab.ui.customerList.CustomerAdapter
import com.my.utils.extensions.defaultOnNullValue
import com.my.utils.extensions.gone
import com.my.utils.extensions.toast
import com.my.utils.extensions.visible
import java.util.Locale

class TransactionHistoryActivity : BaseActivity() {

    var userDataAc: LoginDto.Data? = null
    var transctionHistory = arrayListOf<TransctionAllHistoryDto.Data>()
    var transctionLedger = arrayListOf<LedgerDto.Data>()

    private var adapter: TranasctionHistoryAdapter? = null
    private var adapterLedger: TranasctionLedgerAdapter? = null

    var valueSelected = "0"

    private val viewModel by viewModels<TranscationHitoryViewModel>()
    private lateinit var binding: ActivityTransactionHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDataAc = Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)
        viewModel.uid.set(userDataAc?.id.toString().defaultOnNullValue())

        if (intent.hasExtra("uniqeId")) {
            viewModel.uniqueid.set(intent.getStringExtra("uniqeId"))
            binding.tvTitle.text = "Transaction History  " + "("+intent.getStringExtra("name") + ")".defaultOnNullValue()
        }

        viewModel.customertransactioncalc()

        viewModel.loading.observe(this) {
            when (it) {
                true -> {
                    loader.show()
                }
                false -> {
                    loader.dismiss()
                }
            }
        }

        binding.ivBackS.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.etSearch.doAfterTextChanged {
            if (valueSelected == "1") {
                filterLedger(it.toString())
            } else {
                filter(it.toString())
            }
        }

        viewModel.callCustomerTransactionApi()
        customerTransactionResponse()

        viewModel.responseHistory.observe(this){
            binding.tvTotalConsumer.text = "Total Transaction From Customer Side: " + it.data?.totalTransactionFromCustomerSide.toString().defaultOnNullValue()
            binding.tvTotalPostoffice.text = "Total Postoffice Deposit: " + it.data?.totalPostofficeDeposit.toString().defaultOnNullValue()
            binding.tvTotalLedger.text = "Total Ledger Amount: " + it.data?.totalLedgerAmount.toString().defaultOnNullValue()
            binding.tvTotalRemaining.text = "Total Remaining amount from the Customer: " + it.data?.totalRemainingAmountFromTheCustomer.toString().defaultOnNullValue()
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.text.toString()) {
                    "Transaction List" -> {
                        valueSelected = "0"
                        binding.etSearch.text.clear()
                        transctionHistory.clear()
                        viewModel.callCustomerTransactionApi()
                    }

                    "My Post Deposit" -> {
                        valueSelected = "0"
                        binding.etSearch.text.clear()
                        transctionHistory.clear()
                        viewModel.customerpostofficedeposite()
                    }

                    else -> {
                        valueSelected = "1"
                        binding.etSearch.text.clear()
                        transctionHistory.clear()
                        viewModel.customerledger()
                        customerLedger()
                        showProgressBar()
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<TransctionAllHistoryDto.Data>()
        transctionHistory?.forEach {
            if (it.uniqeId?.toLowerCase()!!.contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(it)
            }
        }
        if (filteredList.isEmpty()) {
            toast("No Data Found..")
        } else {
            adapter?.filterList(filteredList)
        }
    }

    private fun customerTransactionResponse() {
        viewModel.response.observe(this) {
            it.data.let { data ->
                transctionHistory = data
                if (transctionHistory.isEmpty()) {
                    binding.tvNoData.visible()
                } else {
                    binding.tvNoData.gone()
                }
            }

            binding.recycler.viewTreeObserver
                .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        hideProgressBar()
                        binding.recycler.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            adapter = TranasctionHistoryAdapter(transctionHistory)
            binding.recycler.adapter = adapter

        }

    }
    private fun customerLedger() {
        viewModel.responseLedger.observe(this) {
            it.data.let { data ->
                transctionLedger = data
                if (transctionLedger.isEmpty()) {
                    binding.tvNoData.visible()
                } else {
                    binding.tvNoData.gone()
                }
            }

            binding.recycler.viewTreeObserver
                .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        hideProgressBar()
                        binding.recycler.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            adapterLedger = TranasctionLedgerAdapter(transctionLedger)
            binding.recycler.adapter = adapterLedger

        }

    }

    private fun filterLedger(text: String) {
        val filteredList = ArrayList<LedgerDto.Data>()
        transctionLedger?.forEach {
            if (it.uniqeId?.toLowerCase()!!.contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(it)
            }
        }
        if (filteredList.isEmpty()) {
            toast("No Data Found..")
        } else {
            adapterLedger?.filterList(filteredList)
        }
    }
}