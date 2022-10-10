package com.idev.entreumart.data.remote.request

data class RegisterRequest(
    val username: String,
    val email: String,
    val phone_number: String,
    val password: String,
    val c_password: String
)