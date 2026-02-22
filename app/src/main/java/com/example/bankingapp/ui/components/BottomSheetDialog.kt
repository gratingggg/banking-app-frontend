package com.example.bankingapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    activeFilter: String,
    modifier: Modifier = Modifier,
    selectedStatus: String? = null,
    selectedType: String? = null,
    selectedDate: String? = null,
    onTypeSelected: (String?) -> Unit,
    onStatusSelected: (String?) -> Unit,
    onDateSelected: (String?) -> Unit,
    status: List<String>,
    types: List<String>,
    onDismiss: () -> Unit,
    onApply: () -> Unit,
    onCancel: () -> Unit
){
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        containerColor = Color.Gray
    ) {
        Column {
            Text(
                text = when(activeFilter){
                    "Status" -> "Filter by Status"
                    "Type" -> "Filter by Type"
                    "Date" -> "Filter by Date"
                    else -> ""
                },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )

            when(activeFilter){
                "Status" -> {
                    RadioGroup(
                        options = status,
                        selected = selectedStatus
                    ) {
                        onStatusSelected(it)
                    }
                }

                "Type" -> {
                    RadioGroup(
                        options = types,
                        selected = selectedType
                    ) {
                        onTypeSelected(it)
                    }
                }

                "Date" -> {
                    RadioGroup(
                        options = listOf(
                            "This month",
                            "Last 90 days",
                            "Last 180 days"
                        ),
                        selected = selectedDate
                    ) {
                        onDateSelected(it)
                    }
                }
            }

            Button(
                onClick = {
                    onApply()
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF87CEEB)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Apply",
                    color = Color(0xFF00BFFF)
                )
            }

            Button(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = Color(0xFF00BFFF)
                )
            }
        }
    }
}