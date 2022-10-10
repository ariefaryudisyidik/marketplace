package com.idev.entreumart.ui.market.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.response.model.Invoice
import com.idev.entreumart.databinding.ItemShoppingBinding
import com.idev.entreumart.utils.withCurrencyFormat

class ShoppingListAdapter( private val context: Context) :
    ListAdapter<Invoice, ShoppingListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ViewHolder(private val binding: ItemShoppingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(data: Invoice) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.item.picture1)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(ivProduct)
                tvProductName.text = data.item.name
                tvProductPrice.text = data.item.price.withCurrencyFormat()
                tvProductCount.text = context.getString(R.string.product_count, data.quantity)

                val status = when (data.track) {
                    "0" -> "Waiting for confirm payment"
                    "1" -> "Payment confirm, on process"
                    "2" -> "Shipped"
                    "3" -> "Delivered"
                    else -> null
                }

                tvStatus.text = status
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Invoice>() {
            override fun areItemsTheSame(oldItem: Invoice, newItem: Invoice) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Invoice, newItem: Invoice) =
                oldItem == newItem
        }
    }
}