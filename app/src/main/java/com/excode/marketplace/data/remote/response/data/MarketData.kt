package com.excode.marketplace.data.remote.response.data

import com.excode.marketplace.data.remote.response.model.Item
import com.excode.marketplace.data.remote.response.model.User
import com.google.gson.annotations.SerializedName

data class MarketData(
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
    val items: List<Item>,
    val user: User
)