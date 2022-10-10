package com.idev.entreumart.data.remote.response


import com.idev.entreumart.data.remote.response.model.Invoice

data class UploadPaymentResponse(
    val success: Boolean,
    val message: String,
    val data: Invoice
)