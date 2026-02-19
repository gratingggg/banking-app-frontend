package com.example.bankingapp.testing
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.viewModels
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.bankingapp.network.RetrofitInstance
//import com.example.bankingapp.repository.account.AccountRepositoryImpl
//import com.example.bankingapp.repository.auth.AuthRepositoryImpl
//import com.example.bankingapp.repository.customer.CustomerRepositoryImpl
//import com.example.bankingapp.repository.employee.EmployeeRepositoryImpl
//import com.example.bankingapp.repository.loan.LoanRepositoryImpl
//import com.example.bankingapp.repository.notifications.NotificationRepositoryImpl
//import com.example.bankingapp.repository.register.RegisterRepositoryImpl
//import com.example.bankingapp.repository.transaction.TransactionRepositoryImpl
//import com.example.bankingapp.viewmodel.AuthViewModel
//import com.example.bankingapp.viewmodel.CustomerAccountViewModel
//import com.example.bankingapp.viewmodel.CustomerLoanViewModel
//import com.example.bankingapp.viewmodel.CustomerViewModel
//import com.example.bankingapp.viewmodel.EmployeeAccountViewModel
//import com.example.bankingapp.viewmodel.EmployeeLoanViewModel
//import com.example.bankingapp.viewmodel.EmployeeViewModel
//import com.example.bankingapp.viewmodel.NotificationViewModel
//import com.example.bankingapp.viewmodel.RegisterViewModel
//import com.example.bankingapp.viewmodel.TransactionViewModel
//import com.example.bankingapp.viewmodel.factory.AuthViewModelFactory
//import com.example.bankingapp.viewmodel.factory.CustomerAccountViewModelFactory
//import com.example.bankingapp.viewmodel.factory.CustomerLoanViewModelFactory
//import com.example.bankingapp.viewmodel.factory.CustomerViewModelFactory
//import com.example.bankingapp.viewmodel.factory.EmployeeAccountViewModelFactory
//import com.example.bankingapp.viewmodel.factory.EmployeeLoanViewModelFactory
//import com.example.bankingapp.viewmodel.factory.EmployeeViewModelFactory
//import com.example.bankingapp.viewmodel.factory.NotificationViewModelFactory
//import com.example.bankingapp.viewmodel.factory.RegisterViewModelFactory
//import com.example.bankingapp.viewmodel.factory.TransactionViewModelFactory
//
//class MainActivity : ComponentActivity() {
//
//    private val registerViewModel: RegisterViewModel by viewModels {
//        RegisterViewModelFactory(RegisterRepositoryImpl(RetrofitInstance.registerApiService))
//    }
//
//    private val authViewModel: AuthViewModel by viewModels{
//        AuthViewModelFactory(AuthRepositoryImpl(RetrofitInstance.authApiService))
//    }
//
//    private val customerAccountViewModel: CustomerAccountViewModel by viewModels {
//        CustomerAccountViewModelFactory(
//            AccountRepositoryImpl(RetrofitInstance.accountApiService)
//        )
//    }
//
//    private val employeeAccountViewModel: EmployeeAccountViewModel by viewModels{
//        EmployeeAccountViewModelFactory(
//            AccountRepositoryImpl(RetrofitInstance.accountApiService)
//        )
//    }
//
//    private val customerLoanViewModel: CustomerLoanViewModel by viewModels{
//        CustomerLoanViewModelFactory(
//            LoanRepositoryImpl(RetrofitInstance.loanApiService)
//        )
//    }
//
//    private val employeeLoanViewModel: EmployeeLoanViewModel by viewModels{
//        EmployeeLoanViewModelFactory(
//            LoanRepositoryImpl(RetrofitInstance.loanApiService)
//        )
//    }
//
//    private val customerViewModel: CustomerViewModel by viewModels{
//        CustomerViewModelFactory(
//            CustomerRepositoryImpl(RetrofitInstance.customerApiService)
//        )
//    }
//
//    private val employeeViewModel: EmployeeViewModel by viewModels{
//        EmployeeViewModelFactory(
//            EmployeeRepositoryImpl(RetrofitInstance.employeeApiService)
//        )
//    }
//
//    private val transactionViewModel: TransactionViewModel by viewModels{
//        TransactionViewModelFactory(
//            TransactionRepositoryImpl(RetrofitInstance.transactionApiService)
//        )
//    }
//
//    private val notificationViewModel: NotificationViewModel by viewModels{
//        NotificationViewModelFactory(
//            NotificationRepositoryImpl(RetrofitInstance.notificationApiService)
//        )
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        setContent {
//            val scrollState = rememberScrollState()
//            var username by remember {
//                mutableStateOf("rudra")
//            }
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(24.dp)
//                    .verticalScroll(scrollState),
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                LoginUsername {
//                    username = it
//                }
//                TestingRegisterApi(
//                    viewModel = registerViewModel
//                )
//                TestingAuthApi(
//                    viewModel = authViewModel,
//                    username = username,
//                    password = if(username.contentEquals("aditya")) username else "rudra"
//                )
//                TestingCustomerAccountApi(
//                    viewModel = customerAccountViewModel
//                )
//                TestingEmployeeAccountApi(
//                    viewModel = employeeAccountViewModel
//                )
//                TestingCustomerLoanApi(
//                    viewModel = customerLoanViewModel
//                )
//                TestingEmployeeLoanApi(
//                    viewModel = employeeLoanViewModel
//                )
//                TestingEmployeeApi(
//                    viewModel = employeeViewModel
//                )
//                TestingCustomerApi(
//                    viewModel = customerViewModel
//                )
//                TestingTransactionApi(
//                    viewModel = transactionViewModel
//                )
//                TestingNotificationApi(
//                    viewModel = notificationViewModel
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun LoginUsername(onUsernameChange: (String) -> Unit){
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(16.dp)
//    ) {
//        Button(
//            modifier = Modifier.padding(8.dp),
//            onClick = {
//                onUsernameChange("rudra")
//            }
//        ) {
//            Text(
//                text = "Rudra",
//                fontSize = 20.sp,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        Button(
//            modifier = Modifier.padding(8.dp),
//            onClick = {
//                onUsernameChange("parth")
//            }
//        ) {
//            Text(
//                text = "Parth",
//                fontSize = 20.sp,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        Button(
//            modifier = Modifier.padding(8.dp),
//            onClick = {
//                onUsernameChange("aditya")
//            }
//        ) {
//            Text(
//                text = "Aditya",
//                fontSize = 20.sp,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//    }
//}



