package com.idev.entreumart.data.remote.response

import com.idev.entreumart.data.remote.response.model.Item

data class MarketResponse(
    val success: Boolean,
    val data: List<Item>,
    val message: String
)