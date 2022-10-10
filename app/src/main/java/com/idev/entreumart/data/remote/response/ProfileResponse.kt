package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.data.ProfileData


data class ProfileResponse(
    val success: Boolean,
    val message: String,
    val data: ProfileData
)