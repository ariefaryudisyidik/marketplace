package com.excode.marketplace.ui.market.product.buy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.model.Item
import com.excode.marketplace.databinding.ActivityBuyBinding
import com.excode.marketplace.utils.EXTRA_PRODUCT
import com.excode.marketplace.utils.EXTRA_PRODUCT_COUNT
import com.excode.marketplace.utils.EXTRA_TOTAL_PRICE
import com.excode.marketplace.utils.withCurrencyFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCartData()
    }

    private fun getCartData() {
        val product = intent.getParcelableExtra<Item>(EXTRA_PRODUCT) as Item
        val productCount = intent.getIntExtra(EXTRA_PRODUCT_COUNT, 0)
        val totalPrice = intent.getStringExtra(EXTRA_TOTAL_PRICE)

        binding.apply {
            Glide.with(this@BuyActivity)
                .load(product.picture1)
                .placeholder(R.drawable.ic_image)
                .centerCrop()
                .into(ivProduct)
            tvProductName.text = product.name
            tvProductPrice.text = product.price.withCurrencyFormat()
            tvProductCount.text = getString(R.string.product_count, productCount.toString())
            tvTotalPrice.text = totalPrice
        }
    }


}