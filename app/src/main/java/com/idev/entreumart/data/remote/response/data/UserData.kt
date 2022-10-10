package com.idev.entreumart.data.remote.response.data

import com.idev.entreumart.data.remote.response.model.User

data class UserData(
    val user: User,
    val invoices: List<Any>,
)