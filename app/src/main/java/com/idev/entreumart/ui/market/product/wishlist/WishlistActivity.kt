package com.idev.entreumart.ui.market.product.wishlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.idev.entreumart.databinding.ActivityWishlistBinding
import com.idev.entreumart.ui.market.adapter.WishlistAdapter
import com.idev.entreumart.utils.Resource
import com.idev.entreumart.utils.hideProgress
import com.idev.entreumart.utils.showProgress
import com.idev.entreumart.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWishlistBinding
    private val viewModel: WishlistViewModel by viewModels()
    private lateinit var adapter: WishlistAdapter
    private var isWishlist: Boolean? = null

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

    fun getWishlist(token: String) {
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
                        isWishlist = data.data.wishlist.isEmpty()
                        emptyState()
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

    private fun emptyState() {
        binding.layoutEmpty.root.isVisible = isWishlist == true
    }
}