package com.idev.entreumart.ui.market.product.shopping.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.response.model.Invoice
import com.idev.entreumart.databinding.ActivityDetailShoppingBinding
import com.idev.entreumart.utils.EXTRA_INVOICE
import com.idev.entreumart.utils.withCurrencyFormat
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailShoppingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        completeOrder()
    }

    private fun getData() {
        val data = intent.getParcelableExtra<Invoice>(EXTRA_INVOICE) as Invoice
        binding.apply {
            val trackStatus = when (data.track) {
                "0" -> "Waiting for confirm payment"
                "1" -> "Payment confirm, on process"
                "2" -> "Shipped"
                else -> "Delivered"
            }

            val paymentMethod = when (data.paymentMethod) {
                "0" -> "Cash On Delivery (COD)"
                else -> "Bank Transfer"
            }

            val paymentStatus = when (data.paymentStatus) {
                "0" -> "Unpaid"
                "1" -> "Paid but not yet checked"
                else -> "Paid"
            }

            Glide.with(this@DetailShoppingActivity)
                .load(data.item.picture1)
                .placeholder(R.drawable.ic_image)
                .centerCrop()
                .into(ivProduct)
            tvProductName.text = data.item.name
            tvProductPrice.text = data.price.withCurrencyFormat()
            tvProductCount.text = getString(R.string.product_count, data.quantity)
            tvTrackStatus.text = trackStatus
            tvPaymentMethod.text = paymentMethod
            tvPaymentStatus.text = paymentStatus
            tvTotalPayment.text = data.total.withCurrencyFormat()
        }
    }

    private fun completeOrder() {

    }
}