package com.example.bankingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bankingapp.navigation.Navigation
import com.example.bankingapp.ui.screens.SplashScreen
import com.example.bankingapp.ui.theme.BankingAppTheme
import com.example.bankingapp.ui.theme.onSurfaceLight
import com.example.bankingapp.ui.theme.primaryContainerLight

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

