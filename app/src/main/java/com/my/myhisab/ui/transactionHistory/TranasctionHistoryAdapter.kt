package com.my.myhisab.ui.transactionHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.myhisab.databinding.ItemHistoryBinding
import com.my.myhisab.dto.CustomerListDto
import com.my.myhisab.dto.TransctionAllHistoryDto
import com.my.utils.extensions.defaultOnNullValue

class TranasctionHistoryAdapter(
    private var list: ArrayList<TransctionAllHistoryDto.Data>,
) : RecyclerView.Adapter<TranasctionHistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun filterList(filteredlist: ArrayList<TransctionAllHistoryDto.Data>) {
        list = filteredlist
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TransctionAllHistoryDto.Data) = binding.apply {
            binding.tvUniqId.text = "UniqeId: " + data.uniqeId.defaultOnNullValue()
            binding.tvDate.text = "Date: " + data.trDate.defaultOnNullValue()
            binding.tvRemark.text = "Remark: " + data.remark.defaultOnNullValue()
            binding.tvAmount.text = "Amount: " + data.amount.defaultOnNullValue()


        }
    }
}
