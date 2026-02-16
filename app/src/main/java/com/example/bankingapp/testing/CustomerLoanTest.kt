package com.example.bankingapp.testing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.bankingapp.models.account.AccountRequestDto
import com.example.bankingapp.models.loan.LoanRequestDto
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.utils.LoanType
import com.example.bankingapp.viewmodel.CustomerAccountViewModel
import com.example.bankingapp.viewmodel.CustomerLoanViewModel


@Composable
fun TestingCustomerLoanApi(viewModel: CustomerLoanViewModel) {
    val state by viewModel.uiState.collectAsState()

    var accountId by remember {
        mutableLongStateOf(1)
    }

    var loanId by remember {
        mutableLongStateOf(234)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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

        TestingRepayLoan(
            isRepayingLoan = state.isRepayingLoan,
            errorRepayLoan = state.errorRepayLoan,
            repaidLoan = state.repaidLoan
        ) {
            viewModel.repayLoan(loanId, "5000")
        }

        TestingGetLoanTransactions(
            isLoadingTransactions = state.isLoadingTransactions,
            errorTransaction = state.errorTransactions,
            transactions = state.loanTransactions
        ) {
            viewModel.getLoanTransactions(loanId = loanId)
        }
    }
}
