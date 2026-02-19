package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.viewmodel.CustomerLoanViewModel
import com.example.bankingapp.viewmodel.EmployeeLoanViewModel

@Composable
fun TestingEmployeeLoanApi(viewModel: EmployeeLoanViewModel) {
    val state by viewModel.uiState.collectAsState()

    var accountId by remember {
        mutableLongStateOf(1)
    }

    var loanId by remember {
        mutableLongStateOf(234)
    }

    var customerId by remember{
        mutableLongStateOf(1)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "EMPLOYEE LOAN START",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        TestingGetAllLoans(
            isLoadingLoans = state.isLoadingCustomerLoans,
            errorAllLoans = state.errorAllCustomerLoans,
            allLoans = state.allCustomerLoans,
            btnText = "All Customer Loans",
            onGetAllLoans = {
                viewModel.getAllCustomerLoans(
                    customerId = customerId
                )
            },
            onLoansExist = {
                loanId = it
            },
        ) {
            accountId = it
        }

        TestingGetParticularLoan(
            isLoadingLoanDetails = state.isLoadingLoanDetails,
            errorSelectedLoan = state.errorSelectedLoan,
            selectedLoan = state.selectedLoan
        ) {
            viewModel.getParticularLoan(loanId)
        }

        TestingCreateLoan(
            isCreatingLoan = state.isCreatingLoan,
            errorCreateLoan = state.errorCreateLoan,
            createdLoan = state.createdLoan,
            onCreateLoan = {
                viewModel.createLoan(
                    accountId = accountId,
                    tenureInMonths = 12,
                    loanTypeStr = "PERSONAL",
                    principalAmountStr = "20000"
                )
            }
        ) {
            loanId = it
        }


        TestingGetLoanTransactions(
            isLoadingTransactions = state.isLoadingTransactions,
            errorTransaction = state.errorTransactions,
            transactions = state.loanTransactions
        ) {
            viewModel.getLoanTransactions(loanId = loanId)
        }

        TestingGetAllLoans(
            isLoadingLoans = state.isLoadingLoans,
            errorAllLoans = state.errorAllLoans,
            allLoans = state.allLoans,
            onGetAllLoans = {
                viewModel.getAllLoans()
            },
            onLoansExist = {
                loanId = it
            },
        ) {
            accountId = it
        }
        Text(
            text = "EMPLOYEE LOAN END",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}