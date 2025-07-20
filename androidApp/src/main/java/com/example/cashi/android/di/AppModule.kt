package com.example.cashi.android.di

import com.example.cashi.android.repository.PaymentRepositoryImpl
import com.example.cashi.android.viewmodel.PaymentViewModel
import com.example.cashi.data.api.PaymentApiClient
import com.example.cashi.data.repository.PaymentRepository
import com.example.cashi.domain.usecase.PaymentUseCase
import com.example.cashi.domain.validator.PaymentValidator
import com.google.firebase.firestore.FirebaseFirestore
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // HTTP Client
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    // Firebase
    single { FirebaseFirestore.getInstance() }

    // API Client
    single { PaymentApiClient(get()) }

    // Repository
    single<PaymentRepository> { PaymentRepositoryImpl(get(), get()) }

    // Domain
    single { PaymentValidator() }
    single { PaymentUseCase(get(), get()) }

    // ViewModels
    viewModel { PaymentViewModel(get(), get()) }
}