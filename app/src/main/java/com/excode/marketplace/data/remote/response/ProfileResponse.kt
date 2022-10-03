package com.excode.marketplace.data.remote.response


data class ProfileResponse(
    val success: Boolean,
    val message: String,
    val data: ProfileData
)