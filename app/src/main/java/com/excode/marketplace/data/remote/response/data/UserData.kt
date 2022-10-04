package com.excode.marketplace.data.remote.response.data

import com.excode.marketplace.data.remote.response.model.User

data class UserData(
    val user: User,
    val invoices: List<Any>,
)