package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.data.WishlistPostData

data class WishlistPostResponse(
    val success: Boolean,
    val message: String,
    val data: WishlistPostData
)