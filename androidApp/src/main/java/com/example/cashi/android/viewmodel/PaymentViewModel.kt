package com.example.cashi.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashi.domain.usecase.PaymentUseCase
import com.example.cashi.data.models.PaymentRequest
import com.example.cashi.data.models.Transaction
import com.example.cashi.data.repository.PaymentRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val paymentUseCase: PaymentUseCase,
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val successMessage: String? = null,
        val transactions: List<Transaction> = emptyList()
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadTransactionHistory()
    }

    fun sendPayment(request: PaymentRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            paymentUseCase.execute(request).collect { result ->
                when (result) {
                    is PaymentUseCase.Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            successMessage = "Payment sent"
                        )
                    }
                    is PaymentUseCase.Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Invalid Recipient"
                        )
                    }
                    is PaymentUseCase.Result.ValidationError -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.errors.joinToString(", ")
                        )
                    }
                }
            }
        }
    }

    private fun loadTransactionHistory() {
        viewModelScope.launch {
            paymentRepository.getTransactionHistory().collect { transactions ->
                _uiState.value = _uiState.value.copy(transactions = transactions)
            }
        }
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}