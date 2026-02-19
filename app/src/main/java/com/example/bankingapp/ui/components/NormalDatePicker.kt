package com.example.bankingapp.ui.components

import android.media.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.ui.theme.lightCoralPink
import java.nio.file.WatchEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NormalDatePicker(
    fieldValue: String,
    fieldLabel: String,
    fieldPlaceholder: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    isErrorTriggered: Boolean = false,
    onDateSelected: (String) -> Unit
){
    val datePickerState = rememberDatePickerState()
    var showDatePicker by rememberSaveable {
        mutableStateOf(false)
    }
    var isFocused by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = fieldLabel,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        OutlinedTextField(
            value = fieldValue,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                color = Color.Black
            ),
            placeholder = { Text(text = fieldPlaceholder) },
            leadingIcon = {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date Icon",
                    modifier = Modifier
                        .clickable{ showDatePicker = !showDatePicker }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged{ isFocused = it.isFocused }
                .drawBehind{
                    drawLine(
                        color = if(isErrorTriggered) Color.Red else if(isFocused) lightCoralPink else Color.Gray,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        if(showDatePicker){
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDateSelected(
                                datePickerState.selectedDateMillis?.let {
                                    convertMillisToDateString(it)
                                } ?: ""
                            )
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

fun convertMillisToDateString(millis: Long): String{
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}