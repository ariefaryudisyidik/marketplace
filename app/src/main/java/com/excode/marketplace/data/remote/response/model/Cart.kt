package com.excode.marketplace.data.remote.response.model


import com.excode.marketplace.data.remote.response.model.Item
import com.google.gson.annotations.SerializedName

data class Cart(
    val id: Int,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("item_id")
    val itemId: String,
    val quantity: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val item: Item
)