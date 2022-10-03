package com.excode.marketplace.data.remote.response

data class UserResponse(
    val success: Boolean,
    val message: String,
    val data: UserData
)