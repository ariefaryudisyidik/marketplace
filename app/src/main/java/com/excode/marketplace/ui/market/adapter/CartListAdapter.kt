package com.excode.marketplace.ui.market.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.model.Cart
import com.excode.marketplace.databinding.ItemCartBinding
import com.excode.marketplace.ui.market.product.cart.CartViewModel
import com.excode.marketplace.utils.withCurrencyFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    var selectedPosition = -1

    inner class ViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(data: Cart) {
            binding.apply {
                Glide.with(context)
                    .load(data.item.picture1)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(ivProduct)
                tvProductName.text = data.item.name
                tvProductPrice.text = data.item.price.withCurrencyFormat()

                var count = tvProductCount.text.toString().toInt()
                val price = data.item.price.toInt()

                radioButton.isChecked = (adapterPosition == selectedPosition)
                radioButton.setOnCheckedChangeListener { _, b ->
                    if (b) {
                        selectedPosition = adapterPosition
                    }
                    lifecycleOwner.lifecycleScope.launch {
                        delay(1)
                        notifyDataSetChanged()
                    }
                    if (radioButton.isChecked) {
                        val item = data.item
                        viewModel.apply {
                            incrementCount(count * price)
                            getProduct(item)
                            getProductCount(count)
                        }
                    } else {
                        viewModel.decrementCount(count * price)
                    }
                    viewModel.counter.observe(lifecycleOwner) { price ->
                        viewModel.getPrice(price)
                    }
                }

                btnPlus.setOnClickListener {
                    count++
                    tvProductCount.text = count.toString()
                    if (radioButton.isChecked) {
                        viewModel.apply {
                            incrementCount(price)
                            getProductCount(count)
                            counter.observe(lifecycleOwner) { price ->
                                getPrice(price)
                            }
                        }
                    }
                }

                btnMinus.setOnClickListener {
                    if (count > 1) {
                        count--
                        tvProductCount.text = count.toString()
                        if (radioButton.isChecked) {
                            viewModel.apply {
                                decrementCount(price)
                                getProductCount(count)
                                counter.observe(lifecycleOwner) { price ->
                                    getPrice(price)
                                }
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