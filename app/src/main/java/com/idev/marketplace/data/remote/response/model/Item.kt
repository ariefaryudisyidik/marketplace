package com.idev.entreumart.data.remote.response.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Item(
    val id: Int,
    @SerializedName("shop_id")
    val shopId: String,
    val name: String,
    val description: String,
    val price: String,
    val stock: String,
    val sold: String,
    @SerializedName("picture_1")
    val picture1: String?,
    @SerializedName("picture_2")
    val picture2: String?,
    @SerializedName("picture_3")
    val picture3: String?,
    val status: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String,
    val shop: @RawValue Shop?
) : Parcelable