package com.excode.marketplace.data.remote.response

data class ProductResponse(
    val success: Boolean,
    val data: List<ProductData>,
    val message: String
)