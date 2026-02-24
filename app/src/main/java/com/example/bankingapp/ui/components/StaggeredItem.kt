package com.example.bankingapp.ui.components

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun StaggeredItem(
    index: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    val delayMillis = (index * 50).coerceAtMost(300)

    var visible by rememberSaveable {
        mutableStateOf(false)
    }

    val alpha by animateFloatAsState(
        targetValue = if(visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = delayMillis
        ),
        label = "item_alpha_$index"
    )
    val offsetY by animateFloatAsState(
        targetValue = if(visible) 0f else 30f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = delayMillis,
            easing = EaseOut
        )
    )

    LaunchedEffect(true) {
        visible = true
    }

    Box(
        modifier = modifier
            .alpha(alpha)
            .graphicsLayer(translationY = offsetY)
    ){
        content()
    }
}