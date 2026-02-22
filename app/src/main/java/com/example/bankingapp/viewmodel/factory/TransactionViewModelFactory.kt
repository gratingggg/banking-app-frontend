package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.SessionManager
import com.example.bankingapp.repository.transaction.TransactionRepository
import com.example.bankingapp.viewmodel.TransactionViewModel

class TransactionViewModelFactory(
    private val transactionRepository: TransactionRepository,
    private val sessionManager: SessionManager
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(
            transactionRepository = transactionRepository,
            sessionManager = sessionManager
        ) as T
    }
}