package com.example.bankingapp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.bankingapp.utils.Role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SessionManager private constructor(context: Context){

    companion object{
        @Volatile
        private var INSTANCE: SessionManager? = null

        fun getInstance(context: Context): SessionManager{
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SessionManager(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }

    private val appContext = context.applicationContext

    private val Context.dataStore by preferencesDataStore("user_prefs")

    private val TOKEN_KEY = stringPreferencesKey("user_token")
    private val USERNAME_KEY = stringPreferencesKey("user_username")
    private val ROLE_KEY = stringPreferencesKey("user_role")

    suspend fun saveSession(token: String, username: String, role: Role){
        appContext.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USERNAME_KEY] = username
            prefs[ROLE_KEY] = role.name
        }
    }

    suspend fun clearSession(){
        appContext.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    suspend fun isLoggedIn(): Boolean{
        val currentToken = appContext.dataStore.data.map { it[TOKEN_KEY] }.firstOrNull()
        return !currentToken.isNullOrEmpty()
    }

    val token: Flow<String?> = appContext.dataStore.data.map { it[TOKEN_KEY] }
    val username: Flow<String?> = appContext.dataStore.data.map { it[USERNAME_KEY] }
    val role: Flow<Role?> = appContext.dataStore.data.map {
        it[ROLE_KEY]?.let { Role.valueOf(it) }
    }
}