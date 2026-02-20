package com.example.bankingapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    navItemsList: List<BottomNavItem>,
    onNavItemClick: (String) -> Unit
){
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = Color.Transparent
    ) {
        navItemsList.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    onNavItemClick(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.imageVector,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = item.name
                    )
                }
            )
        }
    }
}

