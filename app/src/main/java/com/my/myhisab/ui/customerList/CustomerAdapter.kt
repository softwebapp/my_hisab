package com.my.myhisab.ui.customerList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.myhisab.databinding.ItemCustomerListBinding
import com.my.myhisab.dto.CustomerListDto
import com.my.utils.extensions.defaultOnNullValue

class CustomerAdapter(private var list : ArrayList<CustomerListDto.Data>,
					  private var onItemClicked: ((pos: CustomerListDto.Data) -> Unit)
) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemCustomerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(list[position])
	}

	override fun getItemCount(): Int {
		return list.size
	}

	fun filterList(filteredlist: java.util.ArrayList<CustomerListDto.Data>) {
		list = filteredlist
		notifyDataSetChanged()
	}

    inner class ViewHolder(val binding: ItemCustomerListBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(data: CustomerListDto.Data) = binding.apply {
//			Glide.with(itemView.context).load(data.icon).into(binding.icIcon)
			binding.btnDetail.setOnClickListener {
				onItemClicked(data)
			}
			binding.tvName.text = data.name.defaultOnNullValue()
			binding.tvEmail.text = data.email.defaultOnNullValue()

			binding.tvStartDate.text = "Start Date: " + data.startDate.defaultOnNullValue()
			binding.tvMatureDate.text = "Mature Date: " + data.matureDate.defaultOnNullValue()

			if (data.contactNumber.isNullOrEmpty()){
				binding.tvNumber.text = "Mobile No. _ _ _ _"
			}else{
				binding.tvNumber.text = data.contactNumber.defaultOnNullValue()
			}
		}
	}
}
