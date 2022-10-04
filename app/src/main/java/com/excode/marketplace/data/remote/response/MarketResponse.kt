package com.excode.marketplace.data.remote.response

import com.excode.marketplace.data.remote.response.data.MarketData

data class MarketResponse(
    val success: Boolean,
    val data: List<MarketData>,
    val message: String
)