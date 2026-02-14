package com.example.bankingapp.network

import com.example.bankingapp.models.exception.ErrorResponse
import com.example.bankingapp.network.AuthApiService
import com.example.bankingapp.utils.LocalDateAdapter
import com.example.bankingapp.utils.TokenManager
import com.example.bankingapp.utils.TokenManager.token
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.38:8080/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Content-Type", "application/json")

            if(token != null && !original.url.encodedPath.endsWith("/auth/login")
                && !original.url.encodedPath.endsWith("/auth/register")) {
                request.addHeader("Authorization", "Bearer $token")
            }

            chain.proceed(request.build())
        }.build()

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(LocalDateAdapter())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)
    val registerApiService: RegisterApiService = retrofit.create(RegisterApiService::class.java)
    val accountApiService: AccountApiService = retrofit.create(AccountApiService::class.java)

    fun parseError(errorBody: ResponseBody?): ErrorResponse? {
        val clazz = ErrorResponse::class.java
        return errorBody?.string()?.let {
            moshi.adapter(clazz)?.fromJson(it)
        }
    }
}