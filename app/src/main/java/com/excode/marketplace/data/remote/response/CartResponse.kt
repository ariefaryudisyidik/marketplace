package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.data.CartData


data class CartResponse(
    val success: Boolean,
    val message: String,
    val data: CartData
)