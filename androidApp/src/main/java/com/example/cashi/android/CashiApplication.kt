package com.example.cashi.android

import android.app.Application
import com.google.firebase.FirebaseApp

class CashiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}