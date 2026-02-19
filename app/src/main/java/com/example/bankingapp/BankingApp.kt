package com.example.bankingapp

import android.app.Application
import com.example.bankingapp.network.RetrofitInstance

class BankingApp: Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitInstance.sessionManager = SessionManager.getInstance(this)
    }
}