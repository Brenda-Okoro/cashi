package com.example.cashi.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    val recipientEmail: String,
    val amount: Double,
    val currency: String
)
