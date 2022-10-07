package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.data.CartPostData

data class CartPostResponse(
    val success: Boolean,
    val message: String,
    val data: CartPostData
)