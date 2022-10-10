package com.idev.entreumart.data.remote.response.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val id: Int,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("item_id")
    val itemId: String,
    val quantity: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val item: Item
): Parcelable