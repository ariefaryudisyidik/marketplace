package com.idev.entreumart.data.remote.response.data


import com.google.gson.annotations.SerializedName

data class CheckoutData(
    val code: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("shop_id")
    val shopId: String,
    @SerializedName("item_id")
    val itemId: String,
    val quantity: String,
    val price: String,
    val total: String,
    @SerializedName("payment_status")
    val paymentStatus: Int,
    @SerializedName("payment_method")
    val paymentMethod: String,
    val track: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int
)