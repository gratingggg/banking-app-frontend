package com.example.bankingapp

import android.os.Bundle
import android.util.Log.v
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bankingapp.network.RetrofitInstance
import com.example.bankingapp.repository.account.AccountRepositoryImpl
import com.example.bankingapp.repository.auth.AuthRepositoryImpl
import com.example.bankingapp.repository.register.RegisterRepositoryImpl
import com.example.bankingapp.testing.TestingAuthApi
import com.example.bankingapp.testing.TestingCustomerAccountApi
import com.example.bankingapp.testing.TestingEmployeeAccountApi
import com.example.bankingapp.testing.TestingRegisterApi
import com.example.bankingapp.viewmodel.AuthViewModel
import com.example.bankingapp.viewmodel.CustomerAccountViewModel
import com.example.bankingapp.viewmodel.EmployeeAccountViewModel
import com.example.bankingapp.viewmodel.RegisterViewModel
import com.example.bankingapp.viewmodel.factory.AuthViewModelFactory
import com.example.bankingapp.viewmodel.factory.CustomerAccountViewModelFactory
import com.example.bankingapp.viewmodel.factory.EmployeeAccountViewModelFactory
import com.example.bankingapp.viewmodel.factory.RegisterViewModelFactory

class MainActivity : ComponentActivity() {

    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(RegisterRepositoryImpl(RetrofitInstance.registerApiService))
    }

    private val authViewModel: AuthViewModel by viewModels{
        AuthViewModelFactory(AuthRepositoryImpl(RetrofitInstance.authApiService))
    }

    private val customerAccountViewModel: CustomerAccountViewModel by viewModels {
        CustomerAccountViewModelFactory(
            AccountRepositoryImpl(RetrofitInstance.accountApiService)
        )
    }

    private val employeeAccountViewModel: EmployeeAccountViewModel by viewModels{
        EmployeeAccountViewModelFactory(
            AccountRepositoryImpl(RetrofitInstance.accountApiService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//                TestingRegisterApi(
//                    viewModel = registerViewModel
//                )
                TestingAuthApi(
                    viewModel = authViewModel
                )
                TestingCustomerAccountApi(
                    viewModel = customerAccountViewModel
                )
//                TestingEmployeeAccountApi(
//                    viewModel = employeeAccountViewModel
//                )
            }
        }
    }
}



