package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.data.ProfileData


data class ProfileResponse(
    val success: Boolean,
    val message: String,
    val data: ProfileData
)