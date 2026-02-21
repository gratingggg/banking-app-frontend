package com.example.bankingapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LetterCircleBox(
    letter: String,
    modifier: Modifier = Modifier
){
    Surface(
        modifier = modifier.size(40.dp),
        shape = CircleShape,
        color = getColorFromName(letter)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = letter,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


fun getColorFromName(name: String): Color {
    val colors = listOf(
        Color(0xFF512DA8),
        Color(0xFFD32F2F),
        Color(0xFF1976D2),
        Color(0xFFF57C00),
        Color(0xFF388E3C),
        Color(0xFF7B1FA2),
        Color(0xFF0097A7),
        Color(0xFFC2185B)
    )

    val index = name.hashCode() % colors.size
    return colors[index.coerceAtLeast(0)]
}