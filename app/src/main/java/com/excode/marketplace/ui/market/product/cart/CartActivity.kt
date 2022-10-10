package com.excode.marketplace.ui.market.product.cart

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.excode.marketplace.data.remote.response.model.Cart
import com.excode.marketplace.databinding.ActivityCartBinding
import com.excode.marketplace.ui.market.adapter.CartListAdapter
import com.excode.marketplace.ui.market.product.buy.BuyActivity
import com.excode.marketplace.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartListAdapter
    private lateinit var cartList: List<Cart>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        getToken()
        buy()
    }

    private fun buy() {
        binding.apply {
            viewModel.product.observe(this@CartActivity) { product ->
                viewModel.productCount.observe(this@CartActivity) { count ->
                    btnBuy.setOnClickListener {
                        val totalPrice = tvTotal.text.toString()
                        val intent = Intent(this@CartActivity, BuyActivity::class.java)
                        intent.putExtra(EXTRA_PRODUCT, product)
                        intent.putExtra(EXTRA_PRODUCT_COUNT, count)
                        intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun setTotalPrice() {
        viewModel.price.observe(this) {
            binding.tvTotal.text = it.toString().withCurrencyFormat()
        }
    }

    private fun setupRecyclerView() {
        adapter = CartListAdapter(this, viewModel, this)
        binding.rvCart.adapter = adapter
    }

    private fun getToken() {
        viewModel.token.observe(this) { token ->
            getCarts(token)
            deleteCart(token)
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
                        cartList = data.data.cart
                        adapter.submitList(data.data.cart)
                        setTotalPrice()

                        if (data.data.cart.isEmpty()) {
                            binding.apply {
                                btnBuy.isVisible = false
                                layoutEmpty.root.isVisible = true
                            }
                        } else {
                            binding.apply {
                                btnBuy.isVisible = true
                                layoutEmpty.root.isVisible = false
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    if (token.isNotEmpty()) {
                        showMessage(result.message)
                    }
                    binding.layoutEmpty.root.isVisible = true
                }
            }
        }
    }

    private fun deleteCart(token: String) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val cartId = cartList[viewHolder.adapterPosition].id
                viewModel.deleteCart(token, cartId).observe(this@CartActivity) { result ->
                    when (result) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            getCarts(token)
                        }
                        is Resource.Error -> {}
                    }
                }
            }
        }).attachToRecyclerView(binding.rvCart)
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