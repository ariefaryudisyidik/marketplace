package com.excode.marketplace.data.remote.response

data class UserData(
    val user: User,
    val invoices: List<Any>,
)