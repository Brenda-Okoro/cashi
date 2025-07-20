package com.example.cashi.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import com.example.cashi.data.models.PaymentRequest
import com.example.cashi.util.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionForm(
    onSendPayment: (PaymentRequest) -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    var recipientEmail by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf(Currency.USD) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = recipientEmail,
            onValueChange = { recipientEmail = it },
            label = { Text("Recipient Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCurrency.code,
                onValueChange = {},
                readOnly = true,
                label = { Text("Currency") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Currency.getAllCurrencies().forEach { currency ->
                    DropdownMenuItem(
                        text = { Text("${currency.code} (${currency.symbol})") },
                        onClick = {
                            selectedCurrency = currency
                            expanded = false
                        }
                    )
                }
            }
        }

        errorMessage?.let { message ->
            Card(
                colors = CardDefaults.cardColors(containerColor =
                MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        Button(
            onClick = {
                onSendPayment(
                    PaymentRequest(
                        recipientEmail = recipientEmail,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        currency = selectedCurrency.code
                    )
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Send Payment")
            }
        }
    }
}