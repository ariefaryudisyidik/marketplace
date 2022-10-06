package com.excode.marketplace.ui.market.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.model.Cart
import com.excode.marketplace.databinding.ItemCartBinding
import com.excode.marketplace.ui.market.product.cart.CartViewModel
import com.excode.marketplace.utils.withCurrencyFormat

class CartListAdapter(
    private val context: Context,
    private val viewModel: CartViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    ListAdapter<Cart, CartListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bind(cart)
    }

    inner class ViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Cart) {
            binding.apply {
                Glide.with(context)
                    .load(data.item.picture1)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(ivProduct)
                tvProductName.text = data.item.name
                tvProductPrice.text = data.item.price.withCurrencyFormat()

                var count = 1
                val price = data.item.price.toInt()

                checkBox.setOnClickListener {
                    if (checkBox.isChecked) {
                        viewModel.incrementCount(count * price)
                    } else {
                        viewModel.decrementCount(count * price)
                    }
                    viewModel.counter.observe(lifecycleOwner) {
                        viewModel.getPrice(it)
                    }
                }

                btnPlus.setOnClickListener {
                    count++
                    tvProductCount.text = count.toString()
                    if (checkBox.isChecked) {
                        viewModel.incrementCount(price)
                        viewModel.counter.observe(lifecycleOwner) {
                            viewModel.getPrice(it)
                        }
                    }
                }

                btnMinus.setOnClickListener {
                    if (count > 1) {
                        count--
                        tvProductCount.text = count.toString()
                        if (checkBox.isChecked) {
                            viewModel.decrementCount(price)
                            viewModel.counter.observe(lifecycleOwner) {
                                viewModel.getPrice(it)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart) =
                oldItem == newItem
        }
    }
}