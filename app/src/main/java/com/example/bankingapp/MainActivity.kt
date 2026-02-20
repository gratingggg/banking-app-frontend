package com.example.bankingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.bankingapp.navigation.Navigation
import com.example.bankingapp.ui.containers.CustomerDashboardContainer
import com.example.bankingapp.ui.screens.CustomerDashboardScreen
import com.example.bankingapp.ui.theme.BankingAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BankingAppTheme {
                Navigation()
            }
        }
    }
}

