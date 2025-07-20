package com.example.cashi.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponse(
    val success: Boolean,
    val paymentId: String? = null,
    val message: String
)
