package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.InvoiceData


data class InvoiceResponse(
    val success: Boolean,
    val message: String,
    val data: InvoiceData
)