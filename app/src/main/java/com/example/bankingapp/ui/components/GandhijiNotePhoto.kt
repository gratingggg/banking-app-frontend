package com.example.bankingapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun GandhijiNotePhoto(
    modifier: Modifier = Modifier
){
    val imageHeight = (LocalConfiguration.current.screenWidthDp * (1f/2f)).dp

    Box(
        modifier = modifier
            .clipToBounds()
            .fillMaxWidth()
            .height(imageHeight)
    ){
        Image(
            painter = painterResource(R.drawable.gandhiji_money_note),
            contentDescription = "Currency",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .matchParentSize()
        )

        Text(
            text = "Money",
            style = TextStyle(
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomStart)
        )
    }
}