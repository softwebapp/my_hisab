package com.my.myhisab.ui.addTransaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.myhisab.databinding.ItemUniqueBinding
import com.my.myhisab.dto.UniqueIdDto


class UniqueIdAdapter(
    private var list: ArrayList<UniqueIdDto.Data>,
    private var onItemClicked: ((pos: UniqueIdDto.Data) -> Unit)
) : RecyclerView.Adapter<UniqueIdAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUniqueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun filterList(filterlist: ArrayList<UniqueIdDto.Data>) {
        list = filterlist
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemUniqueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UniqueIdDto.Data) = binding.apply {
            binding.tvTitle.text = data.value +" (" + data.name +" ) : "+ data.perMonth
            itemView.setOnClickListener {
                onItemClicked(data)
            }
        }
    }

}
