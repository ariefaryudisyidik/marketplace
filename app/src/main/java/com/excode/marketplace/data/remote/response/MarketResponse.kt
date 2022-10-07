package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.model.Item

data class MarketResponse(
    val success: Boolean,
    val data: List<Item>,
    val message: String
)