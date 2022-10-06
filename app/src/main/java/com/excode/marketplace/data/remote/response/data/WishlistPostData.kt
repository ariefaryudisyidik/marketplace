package com.excode.marketplace.data.remote.response.data

import com.excode.marketplace.data.remote.response.model.User
import com.excode.marketplace.data.remote.response.model.Wishlist

data class WishlistPostData(
    val wishlist: Wishlist,
    val user: User
)


