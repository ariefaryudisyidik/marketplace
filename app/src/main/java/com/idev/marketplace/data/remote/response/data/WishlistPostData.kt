package com.idev.entreumart.data.remote.response.data

import com.idev.entreumart.data.remote.response.model.User
import com.idev.entreumart.data.remote.response.model.Wishlist

data class WishlistPostData(
    val wishlist: Wishlist,
    val user: User
)


