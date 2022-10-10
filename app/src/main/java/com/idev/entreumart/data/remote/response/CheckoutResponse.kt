package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.CheckoutData


data class CheckoutResponse(
    val success: Boolean,
    val message: String,
    val data: CheckoutData
)