package com.excode.marketplace.ui.market.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.model.Wishlist
import com.excode.marketplace.databinding.ItemWishlistBinding
import com.excode.marketplace.ui.market.product.wishlist.WishlistActivity
import com.excode.marketplace.ui.market.product.wishlist.WishlistViewModel
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.withCurrencyFormat

class WishlistAdapter(
    private val activity: WishlistActivity,
    private val viewModel: WishlistViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
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
        fun bind(wishlist: Wishlist) {
            binding.apply {
                Glide.with(activity)
                    .load(wishlist.item.picture1)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(ivProduct)
                tvProductName.text = wishlist.item.name
                tvProductPrice.text = wishlist.item.price.withCurrencyFormat()
                viewModel.token.observe(lifecycleOwner) { token ->
                    ivWishlist.setOnClickListener {
                        viewModel.deleteWishlist(token, wishlist.id)
                            .observe(lifecycleOwner) { result ->
                                when (result) {
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        activity.getWishlist(token)
                                    }
                                    is Resource.Error -> {}
                                }
                            }
                    }
                }
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