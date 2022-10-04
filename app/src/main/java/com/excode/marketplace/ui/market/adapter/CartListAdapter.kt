package com.excode.marketplace.ui.market.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
                    .transition(DrawableTransitionOptions.withCrossFade()).into(ivProduct)
                tvProductName.text = data.item.name
                tvProductPrice.text = data.item.price.withCurrencyFormat()
                var count = 1
                btnMinus.setOnClickListener {
//                    viewModel.decrementCount()
                    if (count > 1) {
                        count--
                    }
                    tvProductCount.text = count.toString()
                }

                btnPlus.setOnClickListener {
//                    viewModel.incrementCount()
                    count++
                    tvProductCount.text = count.toString()
                }

                viewModel.counter.observe(lifecycleOwner) {
//                    tvProductCount.text = it.toString()
                }


                root.setOnClickListener {
//                        val intent = Intent(context, DetailProductActivity::class.java)
//                        intent.putExtra(EXTRA_PRODUCT, marketData)
//                        context.startActivity(intent)

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