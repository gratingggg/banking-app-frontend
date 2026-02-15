package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.account.EmployeeAccountRepository
import com.example.bankingapp.viewmodel.EmployeeAccountViewModel

@Suppress("UNCHECKED_CAST")
class EmployeeAccountViewModelFactory(
    private val employeeAccountRepository: EmployeeAccountRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmployeeAccountViewModel(employeeAccountRepository) as T
    }
}