package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.data.WishlistData

data class WishlistResponse(
    val success: Boolean,
    val message: String,
    val data: WishlistData
)