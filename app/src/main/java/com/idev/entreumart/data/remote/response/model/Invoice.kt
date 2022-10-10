package com.idev.entreumart.data.remote.response.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Invoice(
    val id: Int,
    val code: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("shop_id")
    val shopId: String,
    @SerializedName("item_id")
    val itemId: String,
    val quantity: String,
    val price: String,
    val total: String,
    @SerializedName("payment_status")
    val paymentStatus: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_upload")
    val paymentUpload: @RawValue Any?,
    val track: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val item: Item?,
    val shop: Shop?
) : Parcelable