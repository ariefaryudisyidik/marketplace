package com.excode.marketplace.ui.market.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.data.MarketData
import com.excode.marketplace.databinding.ItemProductBinding
import com.excode.marketplace.ui.market.product.detail.DetailProductActivity
import com.excode.marketplace.utils.EXTRA_PRODUCT
import com.excode.marketplace.utils.withCurrencyFormat

class ProductGridAdapter(private val context: Context) :
    ListAdapter<MarketData, ProductGridAdapter.ViewHolder>(DIFF_CALLBACK) {

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
        fun bind(marketData: MarketData) {
            binding.apply {
                marketData.items.map { item ->
                    Glide.with(context)
                        .load(item.picture1)
                        .centerCrop()
                        .placeholder(R.drawable.ic_image)
                        .into(ivProduct)
                    tvProductName.text = item.name
                    tvProductPrice.text = item.price.withCurrencyFormat()
                }

                root.setOnClickListener {
                    val intent = Intent(context, DetailProductActivity::class.java)
                    intent.putExtra(EXTRA_PRODUCT, marketData)
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MarketData>() {
            override fun areItemsTheSame(oldItem: MarketData, newItem: MarketData) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MarketData, newItem: MarketData) =
                oldItem == newItem
        }
    }
}