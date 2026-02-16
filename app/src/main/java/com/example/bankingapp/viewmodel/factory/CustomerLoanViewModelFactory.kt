package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.loan.CustomerLoanRepository
import com.example.bankingapp.viewmodel.CustomerLoanViewModel

class CustomerLoanViewModelFactory(
    private val customerLoanRepository: CustomerLoanRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerLoanViewModel(customerLoanRepository) as T
    }
}