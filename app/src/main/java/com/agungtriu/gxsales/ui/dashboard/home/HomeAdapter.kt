package com.agungtriu.gxsales.ui.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agungtriu.gxsales.data.remote.response.StatusesItem
import com.agungtriu.gxsales.databinding.ItemDashboardBinding

class HomeAdapter : ListAdapter<StatusesItem, HomeAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    inner class ViewHolder(private val binding: ItemDashboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StatusesItem, position: Int) {
            binding.tvItemdashboard.text = item.total.toString()
            binding.tvItemdashboardTitle.text = if (position == 0) {
                "Total All Leads "
            } else {
                "Total Leads ${item.name}"
            }
        }

    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<StatusesItem>() {
            override fun areItemsTheSame(oldItem: StatusesItem, newItem: StatusesItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: StatusesItem, newItem: StatusesItem): Boolean =
                oldItem.name == newItem.name

        }
    }
}