package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.transaction.TransactionRepository
import com.example.bankingapp.viewmodel.TransactionViewModel

class TransactionViewModelFactory(
    private val transactionRepository: TransactionRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(transactionRepository) as T
    }
}