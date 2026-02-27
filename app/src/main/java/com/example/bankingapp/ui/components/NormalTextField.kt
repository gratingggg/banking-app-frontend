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
    imageVector: ImageVector? = null,
    isErrorTriggered: Boolean = false,
    enablePhoneOnlyKeyboard: Boolean = false,
    errorMsg: (@Composable () -> Unit)? = null,
    validate: ((String) -> Boolean)? = null,
    textColor: Color = Color.Black,
    onValueChanged: (String) -> Unit
) {
    var isError by rememberSaveable {
        mutableStateOf(false)
    }

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
                isError = validate?.invoke(it) == false
            },
            isError = isError,
            placeholder = { Text(text = fieldLabel) },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (enablePhoneOnlyKeyboard) KeyboardType.Phone else KeyboardType.Text
            ),
            leadingIcon = imageVector?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null
                    )
                }
            },
            textStyle = TextStyle(
                color = textColor
            ),
            supportingText = if (isError) errorMsg else null,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .drawBehind {
                    val textFieldHeight = 56.dp.toPx()
                    drawLine(
                        color = if (isErrorTriggered) Color.Red else if (isFocused) lightCoralPink else Color.Gray,
                        start = Offset(12f, textFieldHeight),
                        end = Offset(size.width, textFieldHeight),
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