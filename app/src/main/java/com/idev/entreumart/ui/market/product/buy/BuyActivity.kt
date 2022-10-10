package com.idev.entreumart.ui.market.product.buy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.response.model.Item
import com.idev.entreumart.databinding.ActivityBuyBinding
import com.idev.entreumart.ui.market.product.shopping.ShoppingActivity
import com.idev.entreumart.utils.EXTRA_PRODUCT
import com.idev.entreumart.utils.EXTRA_PRODUCT_COUNT
import com.idev.entreumart.utils.EXTRA_TOTAL_PRICE
import com.idev.entreumart.utils.withCurrencyFormat
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class BuyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCartData()
        order()
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

    private fun order() {
        binding.btnOrder.setOnClickListener {
            val intent = Intent(this, ShoppingActivity::class.java)
            startActivity(intent)
        }
    }


}