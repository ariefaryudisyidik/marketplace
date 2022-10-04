package com.excode.marketplace.ui.market.product.cart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.excode.marketplace.databinding.ActivityCartBinding
import com.excode.marketplace.ui.market.adapter.CartListAdapter
import com.excode.marketplace.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        getToken()
        setTotal()
    }

    private fun setTotal() {

    }

    private fun setupRecyclerView() {
        adapter = CartListAdapter(this, viewModel, this)
        binding.rvCart.adapter = adapter
    }

    private fun getToken() {
        viewModel.token.observe(this) { token ->
            getCarts(token)
        }
    }

    private fun getCarts(token: String) {
        viewModel.getCarts(token).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data
                    if (data != null) {
//                        var price = 0
//                        data.data.cart.map {
//                            price += it.item.price.toInt()
//                            binding.tvTotal.text = price.toString().withCurrencyFormat()
//                        }
                        viewModel.price.observe(this) {
                            binding.tvTotal.text = it.toString().withCurrencyFormat()
                        }
                        adapter.submitList(data.data.cart)

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