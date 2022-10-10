package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.UserData

data class UserResponse(
    val success: Boolean,
    val message: String,
    val data: UserData
)