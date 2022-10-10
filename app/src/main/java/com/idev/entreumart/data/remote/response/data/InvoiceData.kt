package com.idev.entreumart.data.remote.response.data


import com.idev.entreumart.data.remote.response.model.Invoice
import com.idev.entreumart.data.remote.response.model.User

data class InvoiceData(
    val invoices: List<Invoice>,
    val user: User
)