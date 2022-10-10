package com.idev.entreumart.data.remote.response.data

import com.idev.entreumart.data.remote.response.model.User

data class AuthData(
    val token: String,
    val user: User
)