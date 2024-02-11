package com.agungtriu.gxsales.ui.dashboard.leads

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.databinding.ItemLeadsBinding
import com.agungtriu.gxsales.ui.MainActivity
import com.agungtriu.gxsales.ui.dashboard.formlead.FormLeadFragment.Companion.UPDATE_KEY
import com.agungtriu.gxsales.utils.Date

class LeadsAdapter(private val activity: Activity, private val viewModel: LeadsViewModel) :
    ListAdapter<LeadResponse, LeadsAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemLeadsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun deleteItem(position: Int) {
        val list = currentList.toMutableList()
        list[position].id?.let { viewModel.delete(it) }
    }

    inner class ViewHolder(private val binding: ItemLeadsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LeadResponse) {
            binding.tvItemleadsName.text = item.fullName
            binding.tvItemleadsNumber.text = "#".plus(item.number)
            binding.tvItemleadsAddress.text = item.address
            binding.tvItemleadsDate.text = Date.displayDate(item.createdAt!!)
            binding.tvItemleadsStatus.text = item.status?.name
            binding.tvItemleadsProbability.text = item.probability?.name
            binding.tvItemleadsBranch.text = item.branchOffice?.name
            binding.tvItemleadsStatus.backgroundTintList =
                stateDisplay(itemView.context, item.status?.name)

            binding.tvItemleadsProbability.backgroundTintList =
                stateDisplay(itemView.context, item.probability?.name)

            binding.constraintItemleads.setOnClickListener {
                (activity as MainActivity).navigate(
                    R.id.action_global_to_form_navigation,
                    bundleOf(UPDATE_KEY to item.id)
                )
            }
        }
    }

    private fun stateDisplay(context: Context, state: String? = null): ColorStateList? {
        return when (state) {
            "Scheduled" -> ContextCompat.getColorStateList(context, R.color.color_scheduled)
            "Consideration" -> ContextCompat.getColorStateList(context, R.color.color_consideration)
            "Junk" -> ContextCompat.getColorStateList(context, R.color.color_junk)
            "Pending" -> ContextCompat.getColorStateList(context, R.color.color_pending)
            "Converted" -> ContextCompat.getColorStateList(context, R.color.color_converted)
            "Cancel" -> ContextCompat.getColorStateList(context, R.color.color_cancel)
            else -> ContextCompat.getColorStateList(context, R.color.black)
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
