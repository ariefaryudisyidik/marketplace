package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.WishlistData

data class WishlistResponse(
    val success: Boolean,
    val message: String,
    val data: WishlistData
)