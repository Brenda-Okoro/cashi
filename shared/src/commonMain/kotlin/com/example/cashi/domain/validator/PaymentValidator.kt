package com.example.cashi.domain.validator

import com.example.cashi.data.models.PaymentRequest
import com.example.cashi.util.Currency

class PaymentValidator {

    data class ValidationResult(
        val isValid: Boolean,
        val errors: List<String> = emptyList()
    )

    fun validatePaymentRequest(request: PaymentRequest): ValidationResult {
        val errors = mutableListOf<String>()

        // Validate email
        if (!isValidEmail(request.recipientEmail)) {
            errors.add("Invalid email format")
        }

        // Validate amount
        if (request.amount <= 0) {
            errors.add("Amount must be greater than 0")
        }

        // Validate currency
        if (Currency.fromCode(request.currency) == null) {
            errors.add("Unsupported currency: ${request.currency}")
        }

        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }
}