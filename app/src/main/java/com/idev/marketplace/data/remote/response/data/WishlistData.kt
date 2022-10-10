package com.idev.entreumart.data.remote.response.data

import com.idev.entreumart.data.remote.response.model.User
import com.idev.entreumart.data.remote.response.model.Wishlist

data class WishlistData(
    val wishlist: List<Wishlist>,
    val user: User
)