package com.idev.entreumart.ui.market.product.checkout

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.request.CheckoutRequest
import com.idev.entreumart.data.remote.response.model.Cart
import com.idev.entreumart.databinding.ActivityCheckoutBinding
import com.idev.entreumart.ui.market.product.shopping.ShoppingActivity
import com.idev.entreumart.utils.*
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private val viewModel: CheckoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCartData()
        getToken()
        copyAccountNumber()
    }

    private fun copyAccountNumber() {
        binding.rbTransfer.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("text", "0009101610043209")
            clipboard.setPrimaryClip(clip)
            toast("Nomor rekening berhasil disalin")
        }
    }

    private fun getToken() {
        viewModel.token.observe(this) { token ->
            order(token)
        }
    }

    private fun getCartData() {
        val cart = intent.getParcelableExtra<Cart>(EXTRA_CART) as Cart
        val quantity = intent.getIntExtra(EXTRA_PRODUCT_COUNT, 0)
        val total = intent.getIntExtra(EXTRA_TOTAL_PRICE, 0)

        binding.apply {
            Glide.with(this@CheckoutActivity)
                .load(cart.item.picture1)
                .placeholder(R.drawable.ic_image)
                .centerCrop()
                .into(ivProduct)
            tvProductName.text = cart.item.name
            tvProductPrice.text = cart.item.price.withCurrencyFormat()
            tvProductCount.text = getString(R.string.product_count, quantity.toString())
            tvTotalPrice.text = total.toString().withCurrencyFormat()
        }
    }

    private fun order(token: String) {
        binding.apply {
            btnOrder.setOnClickListener {
                val cart = intent.getParcelableExtra<Cart>(EXTRA_CART) as Cart
                val quantity = intent.getIntExtra(EXTRA_PRODUCT_COUNT, 0)
                val total = intent.getIntExtra(EXTRA_TOTAL_PRICE, 0)

                val checkedRadioButtonId = rgPaymentMethod.checkedRadioButtonId
                if (checkedRadioButtonId != -1) {
                    val paymentMethod = when (checkedRadioButtonId) {
                        R.id.rb_cod -> 0
                        R.id.rb_transfer -> 1
                        else -> null
                    }

                    if (paymentMethod != null) {
                        val checkoutRequest =
                            CheckoutRequest(
                                quantity = quantity,
                                price = cart.item.price.toInt(),
                                total = total,
                                paymentMethod = paymentMethod
                            )

                        viewModel.checkout(token, cart.id, checkoutRequest)
                            .observe(this@CheckoutActivity) { result ->
                                when (result) {
                                    is Resource.Loading -> {
                                        showLoading(true)
                                    }
                                    is Resource.Success -> {
                                        showLoading(false)
                                        navigateToShopping()
                                    }
                                    is Resource.Error -> {
                                        showLoading(false)
                                    }
                                }
                            }
                    }
                } else {
                    toast("Silahkan pilih metode pembayaran!")
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) showProgress(this)
        else hideProgress()
        window.decorView.clearFocus()
    }

    private fun navigateToShopping() {
        startActivity(Intent(this, ShoppingActivity::class.java))
        finish()
    }
}