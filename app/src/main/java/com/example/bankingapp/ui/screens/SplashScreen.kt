package com.example.bankingapp.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bankingapp.R
import com.example.bankingapp.SessionManager
import com.example.bankingapp.navigation.AppDestinations
import com.example.bankingapp.navigation.navigateAndClear
import com.example.bankingapp.network.RetrofitInstance.sessionManager
import com.example.bankingapp.utils.Role
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun SplashScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val context = LocalContext.current
        val sessionManager = remember{
            SessionManager.getInstance(context)
        }

        LaunchedEffect(true) {
            delay(3000)
            val isLoggedIn = sessionManager.isLoggedIn()

            if(isLoggedIn){
                val role = sessionManager.role.firstOrNull()
                val destination = if(role == Role.EMPLOYEE) AppDestinations.EmployeeDashboard.route else AppDestinations.CustomerDashboard.route
                navController.navigateAndClear(
                    route = destination,
                    popUpToRoute = AppDestinations.Splash.route
                )
            } else {
                navController.navigateAndClear(
                    route = AppDestinations.Welcome.route,
                    popUpToRoute = AppDestinations.Splash.route
                )
            }
        }

        val infiniteTransition = rememberInfiniteTransition()
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        val scale by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Reverse
            )
        )

        Image(
            painter = painterResource(R.drawable.small_rudra_with_specs),
            contentDescription = null,
            modifier = Modifier
                .size(175.dp)
                .clip(CircleShape)
                .rotate(rotation)
                .scale(scale)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    clip = true
                ),
            contentScale = ContentScale.Crop
        )
    }
}


