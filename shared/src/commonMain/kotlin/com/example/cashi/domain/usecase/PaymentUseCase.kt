package com.example.cashi.domain.usecase

import com.example.cashi.data.models.PaymentRequest
import com.example.cashi.data.models.Transaction
import com.example.cashi.data.models.TransactionStatus
import com.example.cashi.data.repository.PaymentRepository
import com.example.cashi.domain.validator.PaymentValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

class PaymentUseCase(
    private val paymentRepository: PaymentRepository,
    private val paymentValidator: PaymentValidator
) {

    sealed class Result {
        data class Success(val transaction: Transaction) : Result()
        data class Error(val message: String) : Result()
        data class ValidationError(val errors: List<String>) : Result()
    }

    fun execute(request: PaymentRequest): Flow<Result> = flow {
        // Validate request
        val validationResult = paymentValidator.validatePaymentRequest(request)
        if (!validationResult.isValid) {
            emit(Result.ValidationError(validationResult.errors))
            return@flow
        }

        try {
            // Create transaction object
            val transaction = Transaction(
                recipientEmail = request.recipientEmail,
                amount = request.amount,
                currency = request.currency,
                timestamp = Clock.System.now(),
                status = TransactionStatus.PENDING
            )

            // Process payment through API
            val processedPayment = paymentRepository.processPayment(transaction)

            // Save to Firestore
            paymentRepository.savePayment(processedPayment)

            emit(Result.Success(processedPayment))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }
}