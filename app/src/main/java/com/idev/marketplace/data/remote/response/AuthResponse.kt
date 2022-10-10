package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.AuthData


data class AuthResponse(
    val success: Boolean,
    val data: AuthData,
    val message: String
)