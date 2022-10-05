package com.excode.marketplace.ui.market.product.wishlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
//            viewModel.wishlistId.observe(this) { id ->
//                deleteWishlist(token, id)
//            }
        }
    }

    private fun deleteWishlist(token: String, id: Int) {
        viewModel.addWishList(token, id).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    showMessage(result.message)
                }
                is Resource.Error -> {
                    if (token.isNotEmpty()) {
                        showMessage(result.message)
                    }
                }
            }
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
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
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