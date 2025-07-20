package com.example.cashi.data.models

import kotlinx.datetime.Instant

data class Transaction(
    val id: String = "",
    val recipientEmail: String,
    val amount: Double,
    val currency: String,
    val timestamp: Instant,
    val status: TransactionStatus = TransactionStatus.PENDING
)
