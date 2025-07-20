package com.example.cashi.android.repository

import com.example.cashi.data.api.PaymentApiClient
import com.example.cashi.data.models.PaymentRequest
import com.example.cashi.data.models.Transaction
import com.example.cashi.data.models.TransactionStatus
import com.example.cashi.data.repository.PaymentRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Instant

class PaymentRepositoryImpl(
    private val apiClient: PaymentApiClient,
    private val firestore: FirebaseFirestore
) : PaymentRepository {

    override suspend fun processPayment(transaction: Transaction): Transaction {
        val request = PaymentRequest(
            recipientEmail = transaction.recipientEmail,
            amount = transaction.amount,
            currency = transaction.currency
        )

        val response = apiClient.processPayment(request)

        return transaction.copy(
            id = response.paymentId ?: "",
            status = if (response.success) TransactionStatus.SUCCESS else TransactionStatus.FAILED
        )
    }

    override suspend fun savePayment(transaction: Transaction) {
        val paymentData = mapOf(
            "recipientEmail" to transaction.recipientEmail,
            "amount" to transaction.amount,
            "currency" to transaction.currency,
            "timestamp" to transaction.timestamp.toString(),
            "status" to transaction.status.name
        )

        firestore.collection("transactions")
            .add(paymentData)
            .await()
    }

    override fun getTransactionHistory(): Flow<List<Transaction>> = callbackFlow {
        val listener = firestore.collection("transactions")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val payments = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        Transaction(
                            id = doc.id,
                            recipientEmail = doc.getString("recipientEmail") ?: "",
                            amount = doc.getDouble("amount") ?: 0.0,
                            currency = doc.getString("currency") ?: "",
                            timestamp = Instant.parse(doc.getString
                                ("timestamp") ?: ""),
                            status = TransactionStatus.valueOf(doc.getString(
                                "status") ?: "PENDING")
                        )
                    } catch (e: Exception) {
                        null
                    }
                } ?: emptyList()

                trySend(payments)
            }

        awaitClose { listener.remove() }
    }
}