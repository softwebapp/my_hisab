package com.my.myhisab.ui.allTransaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.myhisab.databinding.ItemCustomerListBinding
import com.my.myhisab.databinding.ItemTranscationBinding
import com.my.myhisab.dto.AllTransactionDto
import com.my.utils.extensions.defaultOnNullValue

class TodayTransactionAdapter(private val list:ArrayList<AllTransactionDto.Data>, private var onItemClicked: ((pos: Int) -> Unit)
) : RecyclerView.Adapter<TodayTransactionAdapter.ViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemTranscationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(list[position])
	}

	override fun getItemCount(): Int {
		return list.size
	}

	inner class ViewHolder(val binding: ItemTranscationBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(data: AllTransactionDto.Data) = binding.apply {
			binding.tvName.text = data.username.defaultOnNullValue()
			binding.tvEmail.text = data.email.defaultOnNullValue()
			binding.tvStartDate.text = data.trDate.defaultOnNullValue()
			binding.tvRemark.text = "Remark: "+data.remark.defaultOnNullValue()
			binding.tvTrans.text = "Transcation by : " +data.transactionBy.defaultOnNullValue()
			binding.tvUniqueId.text = "UniqueId: "+data.uniqeId.defaultOnNullValue()
			binding.tvAmount.text = "Amount\n"+data.amount.defaultOnNullValue()

			if (data.contactnumber.isNullOrEmpty()){
				binding.tvNumber.text = "Mobile No. ____"
			}else{
				binding.tvNumber.text = data.contactnumber.defaultOnNullValue()
			}

			itemView.setOnClickListener {
				onItemClicked(adapterPosition)
			}
		}
	}
}
