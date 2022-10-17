package com.idev.entreumart.ui.market.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.idev.entreumart.R
import com.idev.entreumart.databinding.FragmentHomeBinding
import com.idev.entreumart.ui.market.adapter.ProductGridAdapter
import com.idev.entreumart.ui.market.product.cart.CartActivity
import com.idev.entreumart.ui.market.product.search.SearchActivity
import com.idev.entreumart.ui.market.product.wishlist.WishlistActivity
import com.idev.entreumart.utils.Resource
import com.idev.entreumart.utils.hideProgress
import com.idev.entreumart.utils.showProgress
import com.idev.entreumart.utils.toast
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
        searchProduct()
        navigateToCart()
        navigateToWishList()
    }

    private fun searchProduct() {
        binding.searchView.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
        binding.searchView.setOnSearchClickListener {
            binding.searchView.isIconified = true
            requireActivity().startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
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
                        binding.layoutEmpty.root.isVisible = false
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}