package com.example.bankingapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterChipItem(
    label: String,
    modifier: Modifier = Modifier,
    selectedValue: String? = null,
    onClick: () -> Unit,
    onClear: () -> Unit
){
    val isActive = selectedValue != null
    val containerColor by animateColorAsState(
        targetValue = if(isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        animationSpec = tween(200),
        label = "chip_color"
    )
    val contentColor = if(isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    FilterChip(
        selected = isActive,
        onClick = onClick,
        label = {
            Text(
                text = selectedValue ?: label,
                color = contentColor
            )
        },
        trailingIcon = {
            if(isActive){
                IconButton(
                    onClick = onClear,
                    modifier = Modifier.size(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = contentColor
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "down",
                    tint = contentColor
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = containerColor
        )
    )
}