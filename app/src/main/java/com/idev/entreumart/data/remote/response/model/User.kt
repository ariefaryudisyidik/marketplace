package com.idev.entreumart.data.remote.response.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val role: String,
    val picture: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: @RawValue Any?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
) : Parcelable