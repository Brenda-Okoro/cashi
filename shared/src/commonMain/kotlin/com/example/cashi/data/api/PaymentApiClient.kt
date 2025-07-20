package com.example.cashi.data.api

import com.example.cashi.data.models.PaymentRequest
import com.example.cashi.data.models.PaymentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PaymentApiClient(
    private val httpClient: HttpClient,
    private val baseUrl: String = "http://localhost:3000"
) {

    suspend fun processPayment(request: PaymentRequest): PaymentResponse {
        return httpClient.post("$baseUrl/payments") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}