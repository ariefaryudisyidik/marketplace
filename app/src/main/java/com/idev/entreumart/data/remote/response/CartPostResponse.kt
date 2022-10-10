package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.CartPostData

data class CartPostResponse(
    val success: Boolean,
    val message: String,
    val data: CartPostData
)