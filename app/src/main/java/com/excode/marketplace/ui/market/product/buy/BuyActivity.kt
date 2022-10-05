package com.excode.marketplace.ui.market.product.buy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.excode.marketplace.databinding.ActivityBuyBinding
import com.excode.marketplace.utils.EXTRA_CART
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
        val data = intent.getStringExtra(EXTRA_CART)
        binding.tvTotalPrice.text = data
    }


}