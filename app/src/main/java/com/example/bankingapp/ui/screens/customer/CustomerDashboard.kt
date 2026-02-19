package com.example.bankingapp.ui.screens.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bankingapp.R
import java.nio.file.WatchEvent

@Composable
fun CustomerDashboardScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()
            .height(700.dp)){
            Image(
                painter = painterResource(R.drawable.contor_lines_wavy_divider),
                contentDescription = null,
                modifier = Modifier.matchParentSize()
                    .background(
                        color = Color(0xFFFF8383)
                    ),
                contentScale = ContentScale.Crop
            )
        }
    }
}