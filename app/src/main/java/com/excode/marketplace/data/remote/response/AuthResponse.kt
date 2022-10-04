package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.data.AuthData


data class AuthResponse(
    val success: Boolean,
    val data: AuthData,
    val message: String
)