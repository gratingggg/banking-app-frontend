package com.example.bankingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.R
import com.example.bankingapp.ui.theme.coralPink
import com.example.bankingapp.ui.theme.onPrimaryLight

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    welcomeHeading: String = "Welcome",
    greetingDescription: String,
    continueString: String = "Continue",
    onButtonClicked: () -> Unit
){
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .weight(0.7f)
        ) {
            Image(
                painter = painterResource(R.drawable.contor_lines_wavy_divider),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
                .weight(0.3f)
                .background(
                    color = onPrimaryLight
                )
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = welcomeHeading,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(12.dp)
                )

                Text(
                    text = greetingDescription,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.padding(
                        top = 0.dp,
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 12.dp
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = continueString,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.padding(
                        vertical = 36.dp,
                        horizontal = 24.dp
                    )
                )

                Button(
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = coralPink
                    ),
                    onClick = onButtonClicked,
                    modifier = Modifier.padding(
                        bottom = 24.dp,
                        end = 24.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}