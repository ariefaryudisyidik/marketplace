package com.idev.entreumart.data.remote.response.data


import com.google.gson.annotations.SerializedName

data class ProfileData(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val role: String,
    val picture: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)