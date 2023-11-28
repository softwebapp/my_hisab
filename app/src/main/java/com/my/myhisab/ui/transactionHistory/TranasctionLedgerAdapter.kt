package com.my.myhisab.ui.transactionHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.myhisab.databinding.ItemHistoryBinding
import com.my.myhisab.dto.CustomerListDto
import com.my.myhisab.dto.LedgerDto
import com.my.myhisab.dto.TransctionAllHistoryDto
import com.my.utils.extensions.defaultOnNullValue
import com.my.utils.extensions.visible

class TranasctionLedgerAdapter(
    private var list: ArrayList<LedgerDto.Data>,
) : RecyclerView.Adapter<TranasctionLedgerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun filterList(filteredlist: ArrayList<LedgerDto.Data>) {
        list = filteredlist
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LedgerDto.Data) = binding.apply {
            binding.tvremmAmount.visible()
            binding.tvUniqId.text = "UniqeId: " + data.uniqeId.defaultOnNullValue()
            binding.tvDate.text = "Date: " + data.fromDate.defaultOnNullValue()
            binding.tvRemark.text = "Amount: " + data.amount.defaultOnNullValue()
            binding.tvAmount.text = "Deposit Amount: " + data.depositAmount.defaultOnNullValue()
            binding.tvremmAmount.text = "Remaining Amount: " + data.remainingAmount.defaultOnNullValue()


        }
    }
}
