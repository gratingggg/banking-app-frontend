# Banking App Frontend

A modern Android banking application built with Kotlin and Jetpack Compose, supporting both customer and employee roles with full account, loan, and transaction management capabilities.

## Overview

Banking App Frontend is the client interface for a complete banking system. It communicates with a REST API backend and provides role-based features for managing bank accounts, loan applications, transactions, and notifications.

**Note:** This project requires a backend server running at `http://192.168.1.69:8080/` to function.

## Features

### Authentication & Session Management
- User login and registration
- JWT token-based authentication
- Persistent session management using DataStore
- Automatic token refresh on 401 responses
- Role-based access control (CUSTOMER, EMPLOYEE)

### Customer Features
- **Accounts**
  - View all personal accounts
  - View account details and balance
  - Create new accounts
  - Delete accounts
  - View account transaction history with filtering
  
- **Loans**
  - Apply for loans
  - View all personal loans (with pagination)
  - View specific loan details
  - Filter loans by status, type, and date range
  - View loan transaction history
  - Repay loans
  
- **Transactions**
  - View all transactions (paginated)
  - Filter transactions by status, type, and date range
  - View individual transaction details
  - Transfer funds between accounts
  
- **Notifications**
  - View system notifications

### Employee Features
- **Account Management**
  - View all customer accounts
  - Create accounts on behalf of customers
  - Delete customer accounts
  - Deposit/withdraw funds from customer accounts
  - View account balances
  - View account transaction history
  
- **Loan Management**
  - View all loans in the system
  - View customer-specific loans
  - Create loans for customers
  - Approve/reject loan applications
  - View loan transaction history
  - Filter loans by various criteria

## Tech Stack

### UI & Framework
- **Kotlin** - Primary language
- **Jetpack Compose** - Modern declarative UI framework
- **Material Design 3** - UI components and theming
- **Android Activity** - Single Activity architecture

### Architecture & State Management
- **MVVM Pattern** - Model-View-ViewModel architecture
- **StateFlow** - Reactive state management
- **Coroutines** - Async operations and flow management
- **ViewModel** - Lifecycle-aware business logic

### Networking
- **Retrofit** - HTTP client library
- **OkHttp** - HTTP interceptor for request/response handling
- **Moshi** - JSON serialization/deserialization
- **Moshi Kotlin Adapter** - Kotlin reflection support for Moshi
- **Custom Adapters** - LocalDate, LocalDateTime, and BigDecimal JSON adapters

### Data & Navigation
- **DataStore Preferences** - Secure local data persistence for session tokens
- **Jetpack Navigation Compose** - Screen navigation
- **Jetpack Paging** - Pagination for large datasets

### Other Libraries
- **Google Fonts** - Extended typography support
- **Material Icons Extended** - Additional icon set
- **Core Splash Screen** - Modern app splash screen

## Project Structure

```
app/src/main/java/com/example/bankingapp/
├── models/                           # Data Transfer Objects (DTOs)
│   ├── account/                      # Account-related models
│   │   ├── AccountRequestDto
│   │   ├── AccountResponseDto
│   │   ├── AccountBalanceResponseDto
│   │   └── AccountSummaryDto
│   ├── loan/                         # Loan-related models
│   │   ├── LoanRequestDto
│   │   ├── LoanResponseDto
│   │   └── LoanRepaymentDto
│   ├── transactions/                 # Transaction-related models
│   │   ├── TransactionRequestDto
│   │   ├── TransactionResponseDto
│   │   └── TransactionSummary
│   ├── customer/                     # Customer models
│   ├── employee/                     # Employee models
│   ├── login/                        # Authentication models
│   ├── notifications/                # Notification models
│   ├── exception/                    # Error models
│   │   └── ErrorResponse
│   └── PagedResponse.kt              # Generic pagination wrapper
│
├── network/                          # Retrofit API Services & Configuration
│   ├── RetrofitInstance.kt           # Retrofit singleton with Moshi config
│   ├── AuthApiService.kt             # Login/authentication endpoints
│   ├── RegisterApiService.kt         # Registration endpoints
│   ├── AccountApiService.kt          # Account management endpoints
│   ├── LoanApiService.kt             # Loan endpoints
│   ├── TransactionApiService.kt      # Transaction endpoints
│   ├── CustomerApiService.kt         # Customer info endpoints
│   ├── EmployeeApiService.kt         # Employee endpoints
│   └── NotificationApiService.kt     # Notification endpoints
│
├── viewmodel/                        # MVVM ViewModels
│   ├── AuthViewModel.kt              # Authentication logic
│   ├── RegisterViewModel.kt          # Registration logic
│   ├── CustomerViewModel.kt          # Customer base ViewModel
│   ├── CustomerAccountViewModel.kt   # Customer account operations
│   ├── CustomerLoanViewModel.kt       # Customer loan operations
│   ├── EmployeeViewModel.kt          # Employee base ViewModel
│   ├── EmployeeAccountViewModel.kt   # Employee account operations
│   ├── EmployeeLoanViewModel.kt       # Employee loan operations
│   ├── TransactionViewModel.kt        # Transaction history
│   ├── NotificationViewModel.kt       # Notifications
│   └── factory/                      # ViewModelFactory instances
│
├── ui/                               # Compose UI Layer
│   ├── components/                   # Reusable UI components
│   ├── containers/                   # Layout containers
│   ├── screens/                      # Full screen compositions
│   │   ├── SplashScreen
│   │   ├── LoginScreen
│   │   ├── RegisterScreen
│   │   ├── DashboardScreen
│   │   ├── ProfileScreen
│   │   ├── AccountsScreen
│   │   ├── LoanScreen
│   │   ├── TransactionHistoryScreen
│   │   └── NotificationsScreen
│   └── theme/                        # Material Design 3 theming
│
├── navigation/                       # Navigation graph
│   └── Navigation.kt                 # Compose navigation setup
│
├── repository/                       # Data layer abstraction
│   └── auth/                         # Auth repository
│
├── paging/                           # Pagination sources
│
├── utils/                            # Utilities & Constants
│   ├── Endpoints.kt                  # API endpoint constants
│   ├── Role.kt                       # User role enumeration
│   ├── LoanStatus.kt                 # Loan status enum
│   ├── LoanType.kt                   # Loan type enum
│   ├── TransactionStatus.kt          # Transaction status enum
│   ├── TransactionType.kt            # Transaction type enum
│   ├── ApiResult.kt                  # API result wrapper
│   ├── LocalDateAdapter.kt           # Moshi LocalDate adapter
│   ├── LocalDateTimeAdapter.kt       # Moshi LocalDateTime adapter
│   └── BigDecimalAdapter.kt          # Moshi BigDecimal adapter
│
├── BankingApp.kt                     # Application class - initializes SessionManager
├── MainActivity.kt                   # Single Activity entry point
└── SessionManager.kt                 # Session state management using DataStore
```

## Getting Started

### Prerequisites
- Android Studio (latest)
- JDK 11 or higher
- Android SDK 30+
- Backend server running at `http://192.168.1.69:8080/`

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/gratingggg/banking-app-frontend.git
   cd banking-app-frontend
   ```

2. **Configure Backend URL** (if needed)
   - Open `app/src/main/java/com/example/bankingapp/network/RetrofitInstance.kt`
   - Update `BASE_URL` constant to match your backend server address

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on device/emulator**
   ```bash
   ./gradlew installDebug
   ```
   Or open in Android Studio and run normally.

## Architecture

### MVVM Pattern
The app follows the Model-View-ViewModel architecture:

- **Models**: Data classes representing API responses (DTOs)
- **Views**: Compose screens in the `ui/screens/` package
- **ViewModels**: Business logic and state management in the `viewmodel/` package

### State Management
- **StateFlow** for reactive state
- **Sealed classes** for UI state representation
- Example from `AuthViewModel`:
  ```kotlin
  sealed class LoginUiState {
      object Idle : LoginUiState()
      object Loading : LoginUiState()
      data class Success(val data: LoginResponseDto) : LoginUiState()
      data class Failure(val error: ErrorResponse) : LoginUiState()
  }
  ```

### Repository Pattern
Repository classes abstract the data layer, handling API calls and returning `ApiResult` sealed classes:
```kotlin
sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Failure<T>(val error: ErrorResponse) : ApiResult<T>()
}
```

## API Integration

### Authentication Flow
1. User enters credentials on Login screen
2. `AuthViewModel.login()` calls `AuthRepository.login()`
3. Repository calls `AuthApiService.login()`
4. On success, token is saved via `SessionManager.saveSession()`
5. Token automatically added to all subsequent requests via OkHttp interceptor

### Retrofit Configuration
- **Base URL**: Configurable in `RetrofitInstance.kt`
- **JSON Parsing**: Custom Moshi adapters for `LocalDate`, `LocalDateTime`, and `BigDecimal`
- **Interceptor**: Automatically adds `Authorization: Bearer {token}` header
- **Error Handling**: 401 responses clear session automatically

### API Services
All API services are interface-based using Retrofit annotations:

**Example - AccountApiService:**
- `getAllAccountsByCustomer()` - GET customer accounts
- `getParticularAccountsByCustomer(accountId)` - GET specific account
- `getAllAccountTransactionsByCustomer(accountId, ...)` - GET paginated transactions
- `createAccountByCustomer(accountRequest)` - POST new account
- `getBalanceByCustomer(accountId)` - GET account balance
- Employee equivalents for admin operations

**Pagination Support:**
All endpoints support optional query parameters:
- `page` - Page number
- `size` - Items per page
- `status` - Filter by status (Loan, Transaction)
- `type` - Filter by type (Loan, Transaction)
- `fromDate` / `toDate` - Date range filtering

## Data Persistence

### Session Management
User session (token, username, role) is stored using DataStore Preferences in `SessionManager`:
```kotlin
suspend fun saveSession(token: String, username: String, role: Role)
suspend fun clearSession()
suspend fun isLoggedIn(): Boolean
```

Session data persists across app restarts and is automatically used for API authentication.

## Key Components

### SessionManager
- Singleton instance managing user session state
- Stores token, username, and role
- Provides Flows for reactive session updates
- Initialized in `BankingApp.onCreate()`

### MainActivity
- Single Activity for the entire app
- Sets up Compose content with `Navigation()`
- Applies `BankingAppTheme`

### Navigation
Routes are determined by session state:
- **Unauthenticated** → Splash → Login/Register screens
- **Authenticated (CUSTOMER)** → Dashboard with customer features
- **Authenticated (EMPLOYEE)** → Dashboard with employee features

### ViewModels
Each feature has dedicated ViewModels managing state and logic:
- `AuthViewModel` - Login state
- `CustomerAccountViewModel` - Customer account operations
- `EmployeeAccountViewModel` - Employee account management
- Similar pattern for loans, transactions, etc.

## Dependencies

```gradle
// Compose & UI
androidx.activity.compose
androidx.compose.bom
androidx.material3
androidx.material
androidx.material.icons.extended
androidx.ui.text.google.fonts

// Navigation
androidx.navigation.compose

// Networking
com.squareup.retrofit2:retrofit
com.squareup.retrofit2:converter-moshi
com.squareup.moshi:moshi
com.squareup.moshi:moshi-kotlin

// Data Persistence
androidx.datastore:datastore-preferences
androidx.core:core-splashscreen

// Pagination
androidx.paging:paging-runtime
androidx.paging:paging-compose

// Lifecycle
androidx.lifecycle:lifecycle-runtime-ktx

// Code Generation
com.google.devtools.ksp (KSP plugin)
```

## Backend Requirements

The app expects a REST API at the configured base URL with the following endpoint structure:

### Authentication
- `POST /auth/login` - User login
- `POST /auth/register` - User registration

### Account Management
- `GET /customer/accounts` - List customer accounts
- `POST /customer/accounts` - Create account
- `GET /customer/accounts/{accountId}` - Get account details
- `POST /customer/accounts/{accountId}/delete` - Delete account
- `GET /customer/accounts/{accountId}/balance` - Get balance
- `GET /customer/accounts/{accountId}/transactions` - Get paginated transactions
- Similar endpoints for employee operations

### Loans
- `POST /customer/loans/apply` - Apply for loan
- `GET /customer/loans` - List customer loans
- `GET /customer/loans/{loanId}` - Get loan details
- `POST /customer/loans/{loanId}/repay` - Repay loan
- Employee endpoints for loan management and approval

### Transactions
- `GET /transactions/{transactionId}` - Get transaction details
- `POST /transactions/transfer` - Transfer funds
- `GET /transactions` - List all transactions

Response format for paginated data:
```json
{
  "content": [...],
  "page": {
    "pageNumber": 0,
    "pageSize": 20,
    "totalElements": 100,
    "totalPages": 5
  }
}
```

## Build Information

- **Minimum SDK**: 30
- **Target SDK**: 35
- **Compile SDK**: 36
- **Java/Kotlin Target**: JVM 11
- **Gradle JVM Args**: `-Xmx2048m`

## Known Limitations

- Backend URL is hardcoded in `RetrofitInstance.kt`
- No local caching of API data (only session is cached)
- Tokens are not automatically refreshed when expired
- Limited error recovery mechanisms
- No offline mode support

## Testing

Basic structure for testing is set up via Gradle with:
- `junit` - Unit testing
- `androidx.test.ext:junit` - AndroidX test extensions
- `androidx.test.espresso` - UI testing
- `androidx.compose.ui:ui-test-junit4` - Compose testing

However, comprehensive tests are not yet implemented.

## Development Notes

See `app/todo.txt` for project planning notes and upcoming features.

## License

See LICENSE file for details.

## Contributing

This is a personal project. For issues or suggestions, please open an issue on the repository.
```
