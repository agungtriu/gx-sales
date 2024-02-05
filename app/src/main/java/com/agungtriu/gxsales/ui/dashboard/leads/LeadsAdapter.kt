package com.agungtriu.gxsales.ui.dashboard.leads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.databinding.ItemLeadsBinding
import com.agungtriu.gxsales.utils.Utils

class LeadsAdapter : ListAdapter<LeadResponse, LeadsAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemLeadsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemLeadsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LeadResponse) {
            binding.tvItemleadsName.text = item.fullName
            binding.tvItemleadsNumber.text = "#".plus(item.iDNumber)
            binding.tvItemleadsAddress.text = item.address
            binding.tvItemleadsDate.text = Utils.displayDate(item.createdAt!!)
            binding.tvItemleadsStatus.text = item.status?.name
            binding.tvItemleadsProbability.text = item.probability?.name
            binding.tvItemleadsBranch.text = item.branchOffice?.name

            binding.tvItemleadsStatus.backgroundTintList = when (item.status?.name) {
                "Scheduled" -> ContextCompat.getColorStateList(
                    itemView.context,
                    R.color.color_scheduled
                )

                "Consideration" -> ContextCompat.getColorStateList(
                    itemView.context,
                    R.color.color_consideration
                )

                "Junk" -> ContextCompat.getColorStateList(itemView.context, R.color.color_junk)
                else -> ContextCompat.getColorStateList(itemView.context, R.color.black)
            }

            binding.tvItemleadsProbability.backgroundTintList = when (item.probability?.name) {
                "Pending" -> ContextCompat.getColorStateList(
                    itemView.context,
                    R.color.color_pending
                )

                "Converted" -> ContextCompat.getColorStateList(
                    itemView.context,
                    R.color.color_converted
                )

                "Cancel" -> ContextCompat.getColorStateList(itemView.context, R.color.color_cancel)
                else -> ContextCompat.getColorStateList(itemView.context, R.color.black)
            }
        }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<LeadResponse>() {
            override fun areItemsTheSame(oldItem: LeadResponse, newItem: LeadResponse): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: LeadResponse, newItem: LeadResponse): Boolean =
                oldItem.id == newItem.id

        }
    }
}