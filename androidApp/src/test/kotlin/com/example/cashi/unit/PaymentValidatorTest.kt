package com.example.cashi.unit

import com.example.cashi.data.models.PaymentRequest
import com.example.cashi.domain.validator.PaymentValidator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class PaymentValidatorTest {

    private lateinit var validator: PaymentValidator

    @BeforeEach
    fun setUp() {
        validator = PaymentValidator()
    }

    @Test
    fun `should validate correct payment request`() {
        val request = PaymentRequest(
            recipientEmail = "test@example.com",
            amount = 100.0,
            currency = "USD"
        )

        val result = validator.validatePaymentRequest(request)

        assertTrue(result.isValid)
        assertTrue(result.errors.isEmpty())
    }

    @Test
    fun `should reject invalid email format`() {
        val request = PaymentRequest(
            recipientEmail = "invalid-email",
            amount = 100.0,
            currency = "USD"
        )

        val result = validator.validatePaymentRequest(request)

        assertFalse(result.isValid)
        assertEquals(listOf("Invalid email format"), result.errors)
    }

    @Test
    fun `should reject negative amount`() {
        val request = PaymentRequest(
            recipientEmail = "test@example.com",
            amount = -10.0,
            currency = "USD"
        )

        val result = validator.validatePaymentRequest(request)

        assertFalse(result.isValid)
        assertEquals(listOf("Amount must be greater than 0"), result.errors)
    }

    @Test
    fun `should reject zero amount`() {
        val request = PaymentRequest(
            recipientEmail = "test@example.com",
            amount = 0.0,
            currency = "USD"
        )

        val result = validator.validatePaymentRequest(request)

        assertFalse(result.isValid)
        assertEquals(listOf("Amount must be greater than 0"), result.errors)
    }

    @Test
    fun `should reject unsupported currency`() {
        val request = PaymentRequest(
            recipientEmail = "test@example.com",
            amount = 100.0,
            currency = "INVALID"
        )

        val result = validator.validatePaymentRequest(request)

        assertFalse(result.isValid)
        assertEquals(listOf("Unsupported currency: INVALID"), result.errors)
    }

    @Test
    fun `should return multiple validation errors`() {
        val request = PaymentRequest(
            recipientEmail = "invalid-email",
            amount = -10.0,
            currency = "INVALID"
        )

        val result = validator.validatePaymentRequest(request)

        assertFalse(result.isValid)
        assertEquals(
            listOf(
                "Invalid email format",
                "Amount must be greater than 0",
                "Unsupported currency: INVALID"
            ),
            result.errors
        )
    }
}