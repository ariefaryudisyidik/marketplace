package com.idev.entreumart.data.remote.response.data

import com.idev.entreumart.data.remote.response.model.Cart
import com.idev.entreumart.data.remote.response.model.User

data class CartPostData(
    val cart: Cart,
    val user: User
)