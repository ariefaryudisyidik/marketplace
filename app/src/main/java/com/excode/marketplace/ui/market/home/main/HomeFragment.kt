package com.excode.marketplace.ui.market.home.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.excode.marketplace.R
import com.excode.marketplace.databinding.FragmentHomeBinding
import com.excode.marketplace.ui.market.adapter.ProductGridAdapter
import com.excode.marketplace.ui.market.home.wishlist.WishlistActivity
import com.excode.marketplace.ui.market.product.cart.CartActivity
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.hideProgress
import com.excode.marketplace.utils.showProgress
import com.excode.marketplace.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ProductGridAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        getToken()
        navigateToCart()
        navigateToWishList()
    }

    private fun setupRecyclerView() {
        adapter = ProductGridAdapter(requireContext())
        binding.rvProduct.adapter = adapter
    }

    private fun getToken() {
        viewModel.token.observe(viewLifecycleOwner) { token ->
            getProducts(token)
            getUser(token)
        }
    }

    private fun getProducts(token: String) {
        viewModel.getProducts(token).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data
                    if (data != null) {
                        adapter.submitList(data.data)
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

    private fun getUser(token: String) {
        viewModel.getUser(token).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val data = result.data
                    if (data != null) {
                        val user = data.data.user
                        binding.tvUsername.text = getString(R.string.user_welcome, user.username)
                    }
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) showProgress(requireContext())
        else hideProgress()
        requireActivity().window.decorView.clearFocus()
    }

    private fun showMessage(message: String?) {
        requireActivity().toast(message)
    }

    private fun navigateToWishList() {
        binding.ivWishlist.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }
    }

    private fun navigateToCart() {
        binding.ivCart.setOnClickListener {
            startActivity(Intent(requireContext(), CartActivity::class.java))
        }
    }
}