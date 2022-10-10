package com.idev.entreumart.data.remote.request

import com.google.gson.annotations.SerializedName

data class CheckoutRequest(
    val quantity: Int,
    val price: Int,
    val total: Int,
    @SerializedName("payment_method")
    val paymentMethod: Int
)