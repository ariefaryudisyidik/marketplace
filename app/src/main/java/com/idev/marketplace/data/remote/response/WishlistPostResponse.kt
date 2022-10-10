package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.WishlistPostData

data class WishlistPostResponse(
    val success: Boolean,
    val message: String,
    val data: WishlistPostData
)