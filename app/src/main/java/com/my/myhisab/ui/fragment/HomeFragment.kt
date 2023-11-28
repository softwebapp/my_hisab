package com.my.myhisab.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.my.myhisab.databinding.FragmentHomeBinding
import com.my.myhisab.dto.DashboardDto
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.network.ApiDetails
import com.my.myhisab.network.NetworkConnection
import com.my.myhisab.network.PrefManager
import com.my.myhisab.ui.addTransaction.AddTransactionActivity
import com.my.utils.extensions.action
import com.my.utils.extensions.defaultOnNullValue
import com.my.utils.extensions.getGreetingMessage
import com.my.utils.extensions.gone
import com.my.utils.extensions.isNetworkAvailable
import com.my.utils.extensions.openActivity
import com.my.utils.extensions.snackx
import com.my.utils.extensions.toast
import com.my.utils.extensions.visible

class HomeFragment : Fragment() {

    var userDataAc : LoginDto.Data?=null
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNoon.text = getGreetingMessage()
        userDataAc =  Gson().fromJson(PrefManager.getString(ApiDetails.LogIn), LoginDto.Data::class.java)
        binding.tvName.text = userDataAc?.username.toString()?:"Hi"
//        viewModel.callDashboardApi("1")
        viewModel.callDashboardApi(userDataAc?.id.toString().defaultOnNullValue())

        viewModel.response.observe(requireActivity()){
            setUpData(it)
        }

        binding.btnAddTransaction.setOnClickListener {
            activity?.openActivity<AddTransactionActivity>()
        }

//        if (!isNetworkAvailable(requireContext())){
//            toast("asdasd")
//        }

        checkNetworkConnection()
    }

    private fun checkNetworkConnection() {
//        NetworkConnection(requireContext()).observe(requireActivity()) { isConnected ->
//            if (isConnected) {
//                binding.consOffline.gone()
//                viewModel.callDashboardApi(userDataAc?.id.toString().defaultOnNullValue())
//            } else {
//                binding.consOffline.visible()
//                binding.consOffline.snackx("Check Internet Connection",5000){
//                }
//            }
//        }
    }

    private fun setUpData(it: DashboardDto?) {
        it?.data?.let { data ->
            binding.tvTotalConsumer.text = "Total consumer  : " + data.toalCustomer.defaultOnNullValue()
            binding.tvTotal.text = "Total RD  : " + data.toalRd.defaultOnNullValue()
            binding.tvTotalLIC.text = "Total LIC  : " + data.toalLic.defaultOnNullValue()
        }

        it?.firstHalf?.let {data->
            binding.tvTotalAccount.text = "Total Account\n" + data.totalAccount.defaultOnNullValue()
            binding.tvTotalAmount.text = "Total Amount\n" + data.totalAmount.defaultOnNullValue()
            binding.tvDueAmount.text = "Due Amount\n" + data.dueAmount.defaultOnNullValue()

        }

        it?.secondHalf?.let {data->
            binding.tvTotalAccountS.text = "Total Account\n" + data.totalAccount.defaultOnNullValue()
            binding.tvTotalAmountS.text = "Total Amount\n" + data.totalAmount.defaultOnNullValue()
            binding.tvDueAmountS.text = "Due Amount\n" + data.dueAmount.defaultOnNullValue()
        }

    }


}