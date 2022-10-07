package com.excode.marketplace.ui.market.product.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.model.Item
import com.excode.marketplace.databinding.ActivityDetailProductBinding
import com.excode.marketplace.ui.market.product.cart.CartActivity
import com.excode.marketplace.utils.EXTRA_PRODUCT
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.toast
import com.excode.marketplace.utils.withCurrencyFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: DetailProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDetail()
    }

    private fun showDetail() {
        binding.apply {
            val item = intent.getParcelableExtra<Item>(EXTRA_PRODUCT) as Item
            Glide.with(this@DetailProductActivity)
                .load(item.picture1)
                .into(ivProduct)
            tvProductName.text = item.name
            tvProductPrice.text = item.price.withCurrencyFormat()
            tvProductDesc.text = item.description
            viewModel.token.observe(this@DetailProductActivity) { token ->
                addToCart(token, item.id)
                wishlistStatus(token, item.id)
            }
        }
    }

    private fun wishlistStatus(token: String, itemId: Int) {
        viewModel.getWishlist(token).observe(this) { result ->
            when (result) {
                is Resource.Success -> {
                    binding.apply {
                        btnCart.isVisible = true
                        btnWishlist.isVisible = true
                        layoutEmpty.root.isVisible = false
                    }
                    val data = result.data?.data
                    val wishlists = data?.wishlist
                    var isWishlist = false
                    var wishlistId = 0
                    wishlists?.map { wishlist ->
                        wishlistId = wishlist.id
                        isWishlist = if (wishlists.any { it.itemId.toInt() == itemId }) {
                            binding.btnWishlist.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_wishlist_white_fill, 0, 0, 0
                            )
                            true
                        } else {
                            binding.btnWishlist.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_wishlist_white, 0, 0, 0
                            )
                            false
                        }
                    }

                    binding.btnWishlist.setOnClickListener {
                        isWishlist = if (isWishlist) {
                            binding.btnWishlist.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_wishlist_white, 0, 0, 0
                            )
                            viewModel.deleteWishlist(token, wishlistId).observe(this) {}
                            false
                        } else {
                            binding.btnWishlist.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_wishlist_white_fill, 0, 0, 0
                            )
                            viewModel.addWishlist(token, itemId).observe(this) { result ->
                                when (result) {
                                    is Resource.Success -> {
                                        lifecycleScope.launch {
                                            wishlistId = result.data?.data?.wishlist?.id!!
                                        }
                                    }
                                    else -> {}
                                }
                            }
                            true
                        }
                    }
                }
                else -> {}
            }
        }
    }

    private fun addToCart(token: String, itemId: Int) {
        binding.btnCart.setOnClickListener {
            viewModel.addCart(token, itemId).observe(this) { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        startActivity(Intent(this, CartActivity::class.java))
                        finish()
                    }
                    is Resource.Error -> {
                        startActivity(Intent(this, CartActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun showMessage(message: String?) {
        toast(message)
    }
}