package com.excode.marketplace.ui.market.product.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.excode.marketplace.data.remote.response.data.MarketData
import com.excode.marketplace.databinding.ActivityDetailProductBinding
import com.excode.marketplace.ui.market.product.cart.CartActivity
import com.excode.marketplace.utils.EXTRA_PRODUCT
import com.excode.marketplace.utils.withCurrencyFormat
import dagger.hilt.android.AndroidEntryPoint

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
        addToCart()
    }

    private fun addToCart() {
        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun showDetail() {
        binding.apply {
            val data = intent.getParcelableExtra<MarketData>(EXTRA_PRODUCT) as MarketData
            data.items.map {
                Glide.with(this@DetailProductActivity)
                    .load(it.picture1)
                    .into(ivProduct)
                tvProductName.text = it.name
                tvProductPrice.text = it.price.withCurrencyFormat()
                tvProductDesc.text = it.description
            }
        }
    }
}