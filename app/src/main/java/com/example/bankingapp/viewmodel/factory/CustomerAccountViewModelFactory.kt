package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.account.CustomerAccountRepository
import com.example.bankingapp.viewmodel.CustomerAccountViewModel

class CustomerAccountViewModelFactory(
    private val customerAccountRepository: CustomerAccountRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerAccountViewModel(customerAccountRepository) as T
    }
}