package com.excode.marketplace.ui.market.product.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.data.MarketData
import com.excode.marketplace.databinding.ActivityDetailProductBinding
import com.excode.marketplace.ui.market.product.cart.CartActivity
import com.excode.marketplace.utils.EXTRA_PRODUCT
import com.excode.marketplace.utils.toast
import com.excode.marketplace.utils.withCurrencyFormat
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: DetailProductViewModel by viewModels()
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDetail()
        addToCart()
        toast("onCreate")
    }

    override fun onResume() {
        super.onResume()
        toast("onResume")
    }

    private fun addToCart() {
        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }


    private fun setWishlist(token: String, itemId: Int) {
        viewModel.getWishlist(token).observe(this) { result ->
            var isWishlist = false
            var wishlistId = 0
            result.data?.data?.wishlist?.map { wishlist ->
                wishlistId = wishlist.id
            }
            binding.btnWishlist.setOnClickListener {
                if (wishlistId != null) {
//                    isWishlist = true
                    binding.btnWishlist.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_wishlist_white_fill, 0, 0, 0
                    )
                    viewModel.addWishlist(token, itemId).observe(this) {}
                    toast(wishlistId.toString())
                } else {
                    isWishlist = false
                    binding.btnWishlist.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_wishlist_white, 0, 0, 0
                    )
                    viewModel.deleteWishlist(token, wishlistId).observe(this) {}
                    toast(wishlistId.toString())
                }
            }
        }
    }

    private fun showDetail() {
        binding.apply {
            val data = intent.getParcelableExtra<MarketData>(EXTRA_PRODUCT) as MarketData
            data.items.map { item ->
                Glide.with(this@DetailProductActivity)
                    .load(item.picture1)
                    .into(ivProduct)
                tvProductName.text = item.name
                tvProductPrice.text = item.price.withCurrencyFormat()
                tvProductDesc.text = item.description
                viewModel.token.observe(this@DetailProductActivity) { token ->
                    setWishlist(token, item.id)
                }
            }
        }
    }
}