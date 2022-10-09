package com.excode.marketplace.ui.market.product.search

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.excode.marketplace.data.remote.response.model.Item
import com.excode.marketplace.databinding.ActivitySearchBinding
import com.excode.marketplace.ui.market.adapter.ProductGridAdapter
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.hideProgress
import com.excode.marketplace.utils.showProgress
import com.excode.marketplace.utils.toast
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
        getProduct()

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

    private fun getProduct() {
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
                    showMessage(result.message)
                    binding.layoutEmpty.root.isVisible = true
                }
            }
        }
    }

    private fun showMessage(message: String?) {
        toast(message)
    }
}