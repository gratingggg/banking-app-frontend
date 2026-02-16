package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.loan.EmployeeLoanRepository
import com.example.bankingapp.viewmodel.EmployeeLoanViewModel

class EmployeeLoanViewModelFactory(
    private val employeeLoanRepository: EmployeeLoanRepository
): ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmployeeLoanViewModel(employeeLoanRepository) as T
    }
}