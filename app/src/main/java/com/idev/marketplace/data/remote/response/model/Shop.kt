package com.idev.entreumart.data.remote.response.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    val id: Int,
    @SerializedName("user_id")
    val userId: String,
    val name: String,
    val description: String,
    @SerializedName("banner_image")
    val bannerImage: String?,
    val status: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String,
    val user: User
) : Parcelable