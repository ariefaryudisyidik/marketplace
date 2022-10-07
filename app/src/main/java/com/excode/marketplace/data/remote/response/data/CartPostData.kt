package com.excode.marketplace.data.remote.response.data

import com.excode.marketplace.data.remote.response.model.Cart
import com.excode.marketplace.data.remote.response.model.User

data class CartPostData(
    val cart: Cart,
    val user: User
)