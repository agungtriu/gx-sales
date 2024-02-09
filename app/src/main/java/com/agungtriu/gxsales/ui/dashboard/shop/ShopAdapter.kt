package com.agungtriu.gxsales.ui.dashboard.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.databinding.ItemShopBinding
import com.agungtriu.gxsales.utils.Currency
import com.agungtriu.gxsales.utils.Extension.toCurrency
import com.bumptech.glide.Glide

class ShopAdapter : ListAdapter<Product, ShopAdapter.ViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapter.ViewHolder =
        ViewHolder(
            ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ShopAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.drawable.ic_image)
                .into(binding.ivItemshop)
            binding.tvItemshopName.text = item.name
            binding.tvItemshopPrice.text = item.price.toCurrency(Currency.RUPIAH.symbol)
            binding.tvItemshopStock.text = item.stock.toString().plus(" Pcs")
            binding.tvItemshopType.text = item.type
            binding.tvItemshopTax.text =
                (item.price * item.tax / 100).toCurrency(Currency.RUPIAH.symbol)
                    .plus(" (${item.tax}%)")
        }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

        }
    }
}