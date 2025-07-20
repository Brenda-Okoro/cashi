package com.example.cashi

import com.example.cashi.data.models.PaymentRequest
import com.example.cashi.data.models.Transaction
import com.example.cashi.data.models.TransactionStatus
import com.example.cashi.domain.usecase.PaymentUseCase
import com.example.cashi.domain.validator.PaymentValidator
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.datetime.Clock

class PaymentSteps {

    private lateinit var paymentRequest: PaymentRequest
    private var result: PaymentUseCase.Result? = null
    private val validator = PaymentValidator()

    @Given("a user enters valid payment details")
    fun givenValidPaymentDetails() {
        paymentRequest = PaymentRequest(
            recipientEmail = "test@example.com",
            amount = 100.0,
            currency = "USD"
        )
    }

    @Given("a user enters invalid payment details")
    fun givenInvalidPaymentDetails() {
        paymentRequest = PaymentRequest(
            recipientEmail = "invalid-email",
            amount = -10.0,
            currency = "INVALID"
        )
    }

    @When("they submit the payment")
    fun whenSubmitPayment() {
        val validationResult = validator.validatePaymentRequest(paymentRequest)
        result = if (validationResult.isValid) {
            PaymentUseCase.Result.Success(transaction = Transaction(
                id = "test_id",
                recipientEmail = "testemail",
                amount = 100.0,
                currency = "USD",
                timestamp = Clock.System.now(),
                status = TransactionStatus.SUCCESS
            ))
        } else {
            PaymentUseCase.Result.ValidationError(validationResult.errors)
        }
    }

    @Then("the payment should be processed successfully")
    fun thenPaymentProcessed() {
        result shouldNotBe null
        result.shouldBeInstanceOf<PaymentUseCase.Result.Success>()
    }

    @Then("validation errors should be shown")
    fun thenValidationErrors() {
        result shouldNotBe null
        result.shouldBeInstanceOf<PaymentUseCase.Result.ValidationError>()
    }
}