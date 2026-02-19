package com.example.bankingapp.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.R
import com.example.bankingapp.ui.components.NormalButton
import com.example.bankingapp.ui.components.NormalTextField
import com.example.bankingapp.ui.components.PasswordField
import com.example.bankingapp.ui.theme.coralPink
import com.example.bankingapp.ui.theme.lightCoralPink

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    signInHeaderText: String = "Sign in",
    firstFieldText: String = "Username",
    secondFieldText: String = "Password",
    buttonText: String = "Login",
    signUpText: String = "Sign up",
    isErrorTriggered: Boolean = false,
    onButtonClicked: (String, String) -> Unit,
    onSignUpClicked: () -> Unit
) {
    var firstField by rememberSaveable {
        mutableStateOf("")
    }

    var secondField by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.contor_lines_wavy_divider),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .padding(12.dp)
        ) {
            Text(
                text = signInHeaderText,
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 16.dp, start = 12.dp, end = 48.dp, bottom = 48.dp)
                    .drawBehind {
                        drawLine(
                            color = coralPink,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 4.dp.toPx()
                        )
                    }
            )

            NormalTextField(
                fieldValue = firstField,
                fieldLabel = firstFieldText,
                isErrorTriggered = isErrorTriggered,
                imageVector = Icons.Default.AccountCircle
            ) {
                firstField = it
            }

            PasswordField(
                fieldLabel = secondFieldText,
                isErrorTriggered = isErrorTriggered,
                fieldValue = secondField,
                imageVector = Icons.Default.Lock
            ) {
                secondField = it
            }

            Spacer(modifier = Modifier.weight(1f))

            NormalButton(
                btnText = buttonText,
                enabled = firstField.isNotEmpty() && secondField.isNotEmpty()
            ) {
                onButtonClicked(firstField, secondField)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                )

                Text(
                    text = signUpText,
                    modifier = Modifier.clickable(
                        onClick = onSignUpClicked
                    ),
                    style = TextStyle(
                        color = lightCoralPink,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
    }
}



