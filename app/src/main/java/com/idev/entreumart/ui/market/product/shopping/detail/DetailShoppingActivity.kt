package com.idev.entreumart.ui.market.product.shopping.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.response.model.Invoice
import com.idev.entreumart.databinding.ActivityDetailShoppingBinding
import com.idev.entreumart.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailShoppingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailShoppingBinding
    private val viewModel: DetailShoppingViewModel by viewModels()
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        getToken()
        uploadPayment()
    }

    private fun getToken() {
        viewModel.token.observe(this) { token ->
            completeOrder(token)
        }
    }

    private fun uploadPayment() {
        val intentGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                val selectedImg = it.data?.data as Uri
                val file = uriToFile(selectedImg, this)
                getFile = file
                Glide.with(this)
                    .load(selectedImg)
                    .centerCrop()
                    .into(binding.ivUploadPayment)
            }
        }

        binding.ivUploadPayment.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            intentGalleryLauncher.launch(intent)
        }
    }

    private fun getData() {
        val invoice = intent.getParcelableExtra<Invoice>(EXTRA_INVOICE) as Invoice
        binding.apply {
            val trackStatus = when (invoice.track) {
                "0" -> "Menunggu konfirmasi pembayaran"
                "1" -> "Konfirmasi pembayaran, sedang diproses"
                "2" -> "Dikirim"
                else -> "Delivered"
            }

            val paymentMethod = when (invoice.paymentMethod) {
                "0" -> "Cash On Delivery (COD)"
                else -> "Transfer Bank"
            }

            val paymentStatus = when (invoice.paymentStatus) {
                "0" -> "Belum dibayar"
                "1" -> "Sudah bayar, "
                else -> "Paid"
            }

            Glide.with(this@DetailShoppingActivity)
                .load(invoice.item?.picture1)
                .placeholder(R.drawable.ic_image)
                .centerCrop()
                .into(ivProduct)
            tvProductName.text = invoice.item?.name
            tvProductPrice.text = invoice.price.withCurrencyFormat()
            tvProductCount.text = getString(R.string.product_count, invoice.quantity)
            tvTrackStatus.text = trackStatus
            tvPaymentMethod.text = paymentMethod
            tvPaymentStatus.text = paymentStatus
            tvTotalPayment.text = invoice.total.withCurrencyFormat()

            if (invoice.track != "0") {
                ivUploadPayment.isEnabled = false
                btnCompleteOrder.isVisible = false
                tvTitleUpload.isVisible = false
                tvTitlePaymentStatus.isVisible = true
                Glide.with(this@DetailShoppingActivity)
                    .load(invoice.paymentUpload)
                    .placeholder(R.drawable.ic_image)
                    .centerCrop()
                    .into(ivUploadPayment)
            }

            if (invoice.paymentMethod == "0") {
                ivUploadPayment.isVisible = false
                btnCompleteOrder.isVisible = false
                tvTitlePaymentStatus.isVisible = false
                tvTitleUpload.isVisible = false
            }
        }
    }

    private fun completeOrder(token: String) {
        binding.btnCompleteOrder.setOnClickListener {
            val invoice = intent.getParcelableExtra<Invoice>(EXTRA_INVOICE) as Invoice
            var picture: File? = null
            if (getFile != null) {
                picture = reduceFileImage(getFile as File)
                viewModel.uploadPayment(token, invoice.id, picture).observe(this) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            showLoading(true)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            val data = result.data
                            if (data != null) {
                                toast(data.message)
                            }
                            finish()
                        }
                        is Resource.Error -> {
                            showLoading(false)
                            toast(result.message)
                        }
                    }
                }
            } else {
                toast("Silahkan upload bukti pembayaran")
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) showProgress(this)
        else hideProgress()
    }

}