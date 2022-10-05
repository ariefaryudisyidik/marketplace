package com.excode.marketplace.ui.market.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.excode.marketplace.data.remote.response.model.Wishlist
import com.excode.marketplace.databinding.ItemWishlistBinding
import com.excode.marketplace.utils.withCurrencyFormat

class WishlistAdapter(private val context: Context) :
    ListAdapter<Wishlist, WishlistAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ViewHolder(private val binding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Wishlist) {
            binding.apply {
                Glide.with(context)
                    .load(data.item.picture1)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade()).into(ivProduct)
                tvProductName.text = data.item.name
                tvProductPrice.text = data.item.price.withCurrencyFormat()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Wishlist>() {
            override fun areItemsTheSame(oldItem: Wishlist, newItem: Wishlist) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Wishlist, newItem: Wishlist) =
                oldItem == newItem
        }
    }
}