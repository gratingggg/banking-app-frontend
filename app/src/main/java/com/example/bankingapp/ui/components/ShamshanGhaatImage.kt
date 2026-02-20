package com.example.bankingapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.example.bankingapp.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ShamshanGhaatImage(){
    val imageHeight = (LocalConfiguration.current.screenWidthDp * (2f/3f)).dp

    Box(
        modifier = Modifier
            .clipToBounds()
            .fillMaxWidth()
            .height(imageHeight)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF36B21),
                        Color(0xFFF9a521)
                    )
                )
            )
    ){
        Image(
            painter = painterResource(R.drawable.ic_moonbg),
            contentDescription = "moon",
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.BottomCenter,
            modifier = Modifier.matchParentSize()
        )

        Image(
            painter = painterResource(R.drawable.ic_midbg),
            contentDescription = "mid bg",
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.BottomCenter,
            modifier = Modifier.matchParentSize()
        )

        Image(
            painter = painterResource(R.drawable.ic_outerbg),
            contentDescription = "outer bg",
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.BottomCenter,
            modifier = Modifier.matchParentSize()
        )
    }
}