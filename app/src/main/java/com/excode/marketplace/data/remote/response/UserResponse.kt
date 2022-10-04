package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.data.UserData

data class UserResponse(
    val success: Boolean,
    val message: String,
    val data: UserData
)