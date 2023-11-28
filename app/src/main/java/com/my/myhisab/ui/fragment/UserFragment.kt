package com.my.myhisab.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.my.myhisab.R
import com.my.myhisab.databinding.FragmentUserBinding
import com.my.myhisab.dto.UserDto
import com.my.myhisab.network.PrefManager
import com.my.myhisab.ui.ChangePasswordActivity
import com.my.myhisab.ui.allTransaction.AllTransactionListActivity
import com.my.myhisab.ui.customerList.CustomerListActivity
import com.my.myhisab.ui.login.LoginActivity
import com.my.myhisab.ui.todayTransaction.TodayTransactionActivity
import com.my.utils.extensions.openActivity


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUser.adapter = UserAdapter(getData()) { pos ->
            when (pos) {
                0 -> {
                    activity?.openActivity<CustomerListActivity>()
                }

                1 -> {
                    activity?.openActivity<AllTransactionListActivity>()
                }

                2 -> {
                    activity?.openActivity<TodayTransactionActivity>()
                }

                3 -> {
                    activity?.openActivity<ChangePasswordActivity>()
                }

                4 -> {
                    showPopup()
                }
            }
        }
    }

    private fun getData(): ArrayList<UserDto> {
        val list: ArrayList<UserDto> = ArrayList()
        list.add(UserDto("Customer List", R.drawable.ic_list))
        list.add(UserDto("All Transaction List", R.drawable.ic_alltr))
        list.add(UserDto("Today Transaction List", R.drawable.ic_today))
        list.add(UserDto("Change Password", R.drawable.ic_password))
        list.add(UserDto("Sign Out", R.drawable.ic_logout))
        return list
    }


    private fun showPopup() {
        val alert = AlertDialog.Builder(requireContext())
        alert.setMessage("Are you sure?")
            .setPositiveButton("Logout") { dialog, which ->
                logout()
            }.setNegativeButton("Cancel", null)
        val alert1: AlertDialog = alert.create()
        alert1.show()
    }

    private fun logout() {
        PrefManager.clear()
        activity?.openActivity<LoginActivity>()
    }

}