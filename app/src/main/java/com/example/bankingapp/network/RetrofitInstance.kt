package com.example.bankingapp.network

import com.example.bankingapp.SessionManager
import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.network.AuthApiService
import com.example.bankingapp.utils.BigDecimalAdapter
import com.example.bankingapp.utils.LocalDateAdapter
import com.example.bankingapp.utils.LocalDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.69:8080/"
    lateinit var sessionManager: SessionManager

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Content-Type", "application/json")

            if(!original.url.encodedPath.endsWith("/auth/login")
                && !original.url.encodedPath.endsWith("/auth/register")) {
                val token = runBlocking {
                    sessionManager.token.firstOrNull()
                }
                token?.let {
                    request.addHeader("Authorization", "Bearer $it")
                }
            }

            val response = chain.proceed(request.build())
            if(response.code == 401) runBlocking {
                sessionManager.clearSession()
            }
            return@addInterceptor response
        }.build()

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(LocalDateAdapter())
        .add(BigDecimalAdapter())
        .add(LocalDateTimeAdapter())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)
    val registerApiService: RegisterApiService = retrofit.create(RegisterApiService::class.java)
    val accountApiService: AccountApiService = retrofit.create(AccountApiService::class.java)
    val loanApiService: LoanApiService = retrofit.create(LoanApiService::class.java)
    val employeeApiService: EmployeeApiService = retrofit.create(EmployeeApiService::class.java)
    val customerApiService: CustomerApiService = retrofit.create(CustomerApiService::class.java)
    val transactionApiService: TransactionApiService = retrofit.create(TransactionApiService::class.java)
    val notificationApiService: NotificationApiService = retrofit.create(NotificationApiService::class.java)

    fun parseError(errorBody: ResponseBody?): ErrorResponse? {
        val clazz = ErrorResponse::class.java
        return errorBody?.string()?.let {
            moshi.adapter(clazz)?.fromJson(it)
        }
    }
}