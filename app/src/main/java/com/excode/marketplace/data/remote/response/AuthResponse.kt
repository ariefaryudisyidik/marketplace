package com.excode.marketplace.data.remote.response


data class AuthResponse(
    val success: Boolean,
    val data: AuthData,
    val message: String
)