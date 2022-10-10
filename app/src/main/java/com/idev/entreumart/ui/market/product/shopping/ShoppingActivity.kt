package com.idev.entreumart.ui.market.product.shopping

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.idev.entreumart.databinding.ActivityShoppingBinding
import com.idev.entreumart.ui.market.adapter.ShoppingListAdapter
import com.idev.entreumart.utils.Resource
import com.idev.entreumart.utils.TAG
import com.idev.entreumart.utils.hideProgress
import com.idev.entreumart.utils.showProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingBinding
    private val viewModel: ShoppingViewModel by viewModels()
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getToken()
        setupRecyclerView()
    }

    private fun getToken() {
        viewModel.token.observe(this) { token ->
            getInvoice(token)
        }
    }

    private fun setupRecyclerView() {
        adapter = ShoppingListAdapter(this)
        binding.rvShopping.adapter = adapter
    }

    private fun getInvoice(token: String) {
        viewModel.getInvoice(token).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data
                    if (data != null) {
                        adapter.submitList(data.data.invoices)
                    }
                    Log.d(TAG, "Success: ${data?.message}")
                }
                is Resource.Error -> {
                    showLoading(false)
                    Log.d(TAG, "Error: ${result.data?.message}")
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) showProgress(this)
        else hideProgress()
    }
}