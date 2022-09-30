package com.excode.marketplace.ui.market.wishlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.excode.marketplace.databinding.ActivityWishlistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWishlistBinding
    private val viewModel: WishlistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}