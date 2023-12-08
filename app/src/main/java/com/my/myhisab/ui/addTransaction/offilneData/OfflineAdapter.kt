package com.my.myhisab.ui.addTransaction.offilneData

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.myhisab.databinding.ListBinding
import com.my.myhisab.ui.addTransaction.OfflineTransactionDto

class OfflineAdapter(
    private val courseModalArrayList: ArrayList<OfflineTransactionDto>,
    private var onItemClicked: ((movie: Int, model: OfflineTransactionDto) -> Unit)
) : RecyclerView.Adapter<OfflineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseModalArrayList[position], position)

    }

    override fun getItemCount(): Int {
        return courseModalArrayList.size
    }

    inner class ViewHolder(val binding: ListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OfflineTransactionDto, position: Int) {

            binding.idNumber.text = "Unique ID: "+data.offileUniqueNumber
            binding.idDate.text = "Date: "+data.offlineDate
            binding.idAmount.text = "Amount: "+data.offlineAmount
            binding.idRemark.text = "Remark: "+data.offlineRemark

            binding.btnSubmit.setOnClickListener {
                onItemClicked(position, data)
            }


        }

    }
}