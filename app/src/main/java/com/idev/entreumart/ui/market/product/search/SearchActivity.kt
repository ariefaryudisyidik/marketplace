package com.idev.entreumart.ui.market.product.search

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.idev.entreumart.data.remote.response.model.Item
import com.idev.entreumart.databinding.ActivitySearchBinding
import com.idev.entreumart.ui.market.adapter.ProductGridAdapter
import com.idev.entreumart.utils.Resource
import com.idev.entreumart.utils.hideProgress
import com.idev.entreumart.utils.showProgress
import com.idev.entreumart.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: ProductGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        searchProduct()
        searchResult()
    }

    private fun setupRecyclerView() {
        adapter = ProductGridAdapter(this)
        binding.rvProduct.adapter = adapter
    }

    private fun searchProduct() {
        binding.apply {
            searchView.requestFocus()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(username: String): Boolean {
                    viewModel.searchProduct(username)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
    }

    private fun searchResult() {
        viewModel.products.observe(this) { showProduct(it) }
    }

    private fun showLoading(state: Boolean) {
        if (state) showProgress(this)
        else hideProgress()
        window.decorView.clearFocus()
    }

    private fun showProduct(result: Resource<List<Item>>) {
        binding.apply {
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data
                    if (data != null) {
                        adapter.submitList(data)
                    }
                    binding.layoutEmpty.root.isVisible = false
                }
                is Resource.Error -> {
                    showLoading(false)
                    binding.layoutEmpty.root.isVisible = true
                }
            }
        }
    }
}