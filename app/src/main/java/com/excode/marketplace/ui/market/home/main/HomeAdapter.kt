package com.excode.marketplace.ui.market.home.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.excode.marketplace.data.remote.response.data.MarketData
import com.excode.marketplace.databinding.ItemProductBinding

class HomeAdapter(private val context: Context) :
    ListAdapter<MarketData, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {

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
                    Glide.with(context).load(item.picture1).centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade()).into(ivProduct)
                    tvProductName.text = item.name
                    tvProductPrice.text = item.price
                }

//                root.setOnClickListener {
//                    val intent = Intent(context, DetailActivity::class.java)
//                    intent.putExtra(EXTRA_STORY, storyEntity)
//
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            context as Activity,
//                            Pair(ivItemPhoto, getString(context, R.string.photo)),
//                            Pair(tvItemName, getString(context, R.string.name)),
//                        )
//                    context.startActivity(intent, optionsCompat.toBundle())
//                }
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