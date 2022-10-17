package com.idev.entreumart.ui.market.shopping

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.idev.entreumart.R
import com.idev.entreumart.databinding.FragmentShoppingBinding
import com.idev.entreumart.ui.market.adapter.ShoppingListAdapter
import com.idev.entreumart.utils.Resource
import com.idev.entreumart.utils.hideProgress
import com.idev.entreumart.utils.showProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShoppingViewModel by viewModels()
    private lateinit var adapter: ShoppingListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShoppingBinding.bind(view)

        getToken()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getToken() {
        viewModel.token.observe(viewLifecycleOwner) { token ->
            getInvoice(token)
        }
    }

    private fun setupRecyclerView() {
        adapter = ShoppingListAdapter(requireActivity())
        binding.rvShopping.adapter = adapter
    }

    private fun getInvoice(token: String) {
        viewModel.getInvoice(token).observe(viewLifecycleOwner) { result ->
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
                }
                is Resource.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) showProgress(requireContext())
        else hideProgress()
    }
}