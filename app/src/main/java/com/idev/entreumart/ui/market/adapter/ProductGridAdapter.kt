package com.idev.entreumart.ui.market.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.response.model.Item
import com.idev.entreumart.databinding.ItemProductBinding
import com.idev.entreumart.ui.market.product.detail.DetailProductActivity
import com.idev.entreumart.utils.EXTRA_POSITION
import com.idev.entreumart.utils.EXTRA_PRODUCT
import com.idev.entreumart.utils.withCurrencyFormat

class ProductGridAdapter(private val context: Context) :
    ListAdapter<Item, ProductGridAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                Glide.with(context)
                    .load(item.picture1)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(ivProduct)
                tvProductName.text = item.name
                tvProductPrice.text = item.price.withCurrencyFormat()

                root.setOnClickListener {
                    val intent = Intent(context, DetailProductActivity::class.java)
                    intent.putExtra(EXTRA_PRODUCT, item)
                    intent.putExtra(EXTRA_POSITION, adapterPosition)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item) =
                oldItem == newItem
        }
    }
}