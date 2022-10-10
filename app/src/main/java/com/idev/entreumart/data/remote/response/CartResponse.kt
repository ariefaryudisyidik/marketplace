package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.CartData


data class CartResponse(
    val success: Boolean,
    val message: String,
    val data: CartData
)