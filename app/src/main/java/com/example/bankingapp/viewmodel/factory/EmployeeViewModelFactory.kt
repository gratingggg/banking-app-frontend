package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.employee.EmployeeRepository
import com.example.bankingapp.viewmodel.EmployeeViewModel

class EmployeeViewModelFactory(
    private val employeeRepository: EmployeeRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmployeeViewModel(employeeRepository) as T
    }
}