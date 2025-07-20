package com.example.cashi.data.repository

import com.example.cashi.data.models.Transaction
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun processPayment(transaction: Transaction): Transaction
    suspend fun savePayment(transaction: Transaction)
    fun getTransactionHistory(): Flow<List<Transaction>>
}