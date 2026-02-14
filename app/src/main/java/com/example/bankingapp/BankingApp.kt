package com.example.bankingapp

import android.app.Application
import com.example.bankingapp.utils.TokenManager

class BankingApp: Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(this)
    }
}