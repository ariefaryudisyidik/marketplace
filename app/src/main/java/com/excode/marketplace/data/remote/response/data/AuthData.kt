package com.excode.marketplace.data.remote.response.data

import com.excode.marketplace.data.remote.response.model.User

data class AuthData(
    val token: String,
    val user: User
)