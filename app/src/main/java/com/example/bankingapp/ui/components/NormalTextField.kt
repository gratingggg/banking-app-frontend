package com.example.bankingapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.ui.theme.lightCoralPink

@Composable
fun NormalTextField(
    fieldValue: String,
    fieldLabel: String,
    imageVector: ImageVector,
    isErrorTriggered: Boolean = false,
    enablePhoneOnlyKeyboard: Boolean = false,
    onValueChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(6.dp)
    ) {
            Text(
                text = fieldLabel,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

        var isFocused by rememberSaveable {
            mutableStateOf(false)
        }

        OutlinedTextField(
            value = fieldValue,
            onValueChange = {
                onValueChanged(it)
            },
            textStyle = TextStyle(
                color = Color.Black
            ),
            placeholder = { Text(text = fieldLabel) },
            keyboardOptions = KeyboardOptions(
                keyboardType = if(enablePhoneOnlyKeyboard) KeyboardType.Phone else KeyboardType.Text
            ),
            leadingIcon = {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .drawBehind {
                    drawLine(
                        color = if(isErrorTriggered) Color.Red else if (isFocused) lightCoralPink else Color.Gray,
                        start = Offset(12f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )
    }
}