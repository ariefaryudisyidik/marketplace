package com.excode.marketplace.ui.market.product.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.excode.marketplace.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}