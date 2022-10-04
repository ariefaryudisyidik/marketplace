package com.excode.marketplace.ui.market.home.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.excode.marketplace.R
import com.excode.marketplace.databinding.FragmentHomeBinding
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
    private lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        getToken()
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter(requireContext())
        binding.rvProduct.adapter = adapter
    }

    private fun getToken() {
        viewModel.token.observe(viewLifecycleOwner) { token ->
            getProducts(token)
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

    private fun showLoading(state: Boolean) {
        if (state) showProgress(requireContext())
        else hideProgress()
        requireActivity().window.decorView.clearFocus()
    }

    private fun showMessage(message: String?) {
        requireActivity().toast(message)
    }
}