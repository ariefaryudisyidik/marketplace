package com.excode.marketplace.ui.market.product.wishlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.excode.marketplace.databinding.ActivityWishlistBinding
import com.excode.marketplace.ui.market.adapter.WishlistAdapter
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.hideProgress
import com.excode.marketplace.utils.showProgress
import com.excode.marketplace.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWishlistBinding
    private val viewModel: WishlistViewModel by viewModels()
    private lateinit var adapter: WishlistAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        getToken()
    }

    private fun setupRecyclerView() {
        adapter = WishlistAdapter(this, viewModel, this)
        binding.rvWishlist.adapter = adapter
    }

    private fun getToken() {
        viewModel.token.observe(this) { token ->
            getWishlist(token)
        }
    }

    private fun getWishlist(token: String) {
        viewModel.getWishlist(token).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data
                    if (data != null) {
                        adapter.submitList(data.data.wishlist)
                        binding.layoutEmpty.root.isVisible = false
                        if (data.data.wishlist.isEmpty()) {
                            binding.apply {
                                layoutEmpty.root.isVisible = true
                            }
                        }
                    }

                }
                is Resource.Error -> {
                    showLoading(false)
                    binding.layoutEmpty.root.isVisible = true
                    if (token.isNotEmpty()) {
                        showMessage(result.message)
                    }
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) showProgress(this)
        else hideProgress()
        window.decorView.clearFocus()
    }

    private fun showMessage(message: String?) {
        toast(message)
    }
}