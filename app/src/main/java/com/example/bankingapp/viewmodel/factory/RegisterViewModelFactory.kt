package com.example.bankingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bankingapp.repository.register.RegisterRepository
import com.example.bankingapp.viewmodel.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory(
    private val registerRepository: RegisterRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(registerRepository) as T
    }
}