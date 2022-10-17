package com.idev.entreumart.data.remote.response.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wishlist(
    val id: Int,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("item_id")
    val itemId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val item: Item,
    val user: User
) : Parcelable