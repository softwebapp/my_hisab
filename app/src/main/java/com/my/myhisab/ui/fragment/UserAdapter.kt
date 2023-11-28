package com.my.myhisab.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.myhisab.databinding.ListUserBinding
import com.my.myhisab.dto.UserDto

class UserAdapter(private var list: ArrayList<UserDto>,
				  private var onItemClicked: ((pos: Int) -> Unit)
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(list[position])
	}

	override fun getItemCount(): Int {
		return list.size
	}

	inner class ViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(data: UserDto) = binding.apply {
			binding.tvTitle.text = data.name
			Glide.with(itemView.context).load(data.icon).into(binding.icIcon)
			itemView.setOnClickListener {
				onItemClicked(adapterPosition)
			}
		}
	}
}
