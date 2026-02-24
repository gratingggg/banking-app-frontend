package com.example.bankingapp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable

@Composable
fun TransactionListSection(
    isLoading: Boolean,
    content: @Composable () -> Unit
){
    AnimatedContent(
        targetState = isLoading,
        transitionSpec = {
            fadeIn(tween(300)) togetherWith fadeOut(tween(300))
        },
        label = "animated_transaction_list"
    ) { isLoading ->
        if(isLoading){
            TransactionSkeletonList()
        } else {
            content()
        }
    }
}