package com.example.bankingapp.utils

object Endpoints {
    const val REGISTER = "/api/auth/register";
    const val LOGIN = "/api/auth/login";



    const val CUSTOMER_ME = "/api/customer/me";



    const val EMPLOYEE_ME = "/api/employee/me";
    const val EMPLOYEE_LOANS_PENDING = "/api/employee/loans/pending";
    const val EMPLOYEE_LOANS_PROCESS = "/api/employee/loans/{loanId}/process";



    const val CUSTOMER_ACCOUNTS_ALL = "/api/customer/accounts";
    const val CUSTOMER_ACCOUNT_PARTICULAR = "/api/customer/accounts/{accountId}";
    const val CUSTOMER_ACCOUNT_TRANSACTION_ALL = "/api/customer/accounts/{accountId}/transactions";
    const val CUSTOMER_ACCOUNT_CREATE = "/api/customer/accounts";
    const val CUSTOMER_ACCOUNT_DELETE = "/api/customer/accounts/{accountId}/close";
    const val CUSTOMER_ACCOUNT_BALANCE = "/api/customer/accounts/{accountId}/balance";

    const val EMPLOYEE_ACCOUNTS_ALL = "/api/employee/customer/{customerId}/accounts";
    const val EMPLOYEE_ACCOUNT_PARTICULAR = "/api/employee/accounts/{accountId}";
    const val EMPLOYEE_ACCOUNT_TRANSACTIONS_ALL = "/api/employee/accounts/{accountId}/transactions";
    const val EMPLOYEE_ACCOUNT_CREATE = "/api/employee/customer/{customerId}/accounts";
    const val EMPLOYEE_ACCOUNT_DELETE = "/api/employee/accounts/{accountId}/close";
    const val EMPLOYEE_ACCOUNT_BALANCE = "/api/employee/accounts/{accountId}/balance";
    const val EMPLOYEE_CUSTOMER_TRANSACTION_ALL = "/api/employee/customer/{customerId}/accounts/transactions";
    const val EMPLOYEE_ACCOUNT_DEPOSIT = "/api/employee/accounts/{accountId}/deposit";
    const val EMPLOYEE_ACCOUNT_WITHDRAWAL = "/api/employee/accounts/{accountId}/withdrawal";



    const val CUSTOMER_LOAN_APPLY = "/api/customer/loan/apply"
    const val CUSTOMER_LOAN_ALL = "/api/customer/loan";
    const val CUSTOMER_LOAN_REPAY = "/api/customer/loan/repay";
    const val CUSTOMER_LOAN_PARTICULAR = "/api/customer/loan/{loanId}";
    const val CUSTOMER_LOAN_TRANSACTIONS = "/api/customer/loan/{loanId}/transactions";

    const val EMPLOYEE_LOAN_APPLY = "/api/employee/customer/account/{accountId}/loan/apply";
    const val EMPLOYEE_LOAN_ALL = "/api/employee/customer/{customerId}/loan";
    const val EMPLOYEE_LOAN_PARTICULAR = "/api/employee/customer/loan/{loanId}";
    const val EMPLOYEE_LOAN_TRANSACTIONS = "/api/employee/customer/loan/{loanId}/transactions";



    const val TRANSACTIONS_CUSTOMER_TRANSFER = "/api/customer/transactions/transfer";
    const val TRANSACTION_CUSTOMER = "/api/customer/transactions/{transactionId}";

    const val TRANSACTIONS_EMPLOYEE_TRANSFER = "/api/employee/transactions/transfer";
    const val TRANSACTION_EMPLOYEE = "/api/employee/transactions/{transactionId}";



    const val NOTIFICATIONS_ALL = "/api/notifications";
    const val NOTIFICATION_PARTICULAR = "/api/notifications/{notificationId}";
    const val NOTIFICATION_READ_ALL = "/api/notifications/read";
}