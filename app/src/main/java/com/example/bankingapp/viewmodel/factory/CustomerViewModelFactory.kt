package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.customer.CustomerRepository
import com.example.bankingapp.viewmodel.CustomerViewModel

class CustomerViewModelFactory(
    private val customerRepository: CustomerRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerViewModel(customerRepository) as T
    }
}