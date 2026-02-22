package com.example.bankingapp.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterChipRow(
    modifier: Modifier = Modifier,
    selectedStatus: String? = null,
    selectedType: String? = null,
    selectedTimeSpan: String? = null,
    status: List<String>,
    type: List<String>,
    onStatusSelected: (String?) -> Unit,
    onTypeSelected: (String?) -> Unit,
    onDateSelected: (String?) -> Unit,
    onApply: () -> Unit
) {
    var activeFilter by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var scroll = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scroll)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FilterChipItem(
            label = "Status",
            selectedValue = selectedStatus,
            onClick = {
                activeFilter = "Status"
            }
        ) {
            onStatusSelected(null)
        }

        FilterChipItem(
            label = "Type",
            selectedValue = selectedType,
            onClick = {
                activeFilter = "Type"
            }
        ) {
            onTypeSelected(null)
        }

        FilterChipItem(
            label = "Date",
            selectedValue = selectedTimeSpan,
            onClick = {
                activeFilter = "Date"
            }
        ) {
            onDateSelected(null)
        }
    }

    if (activeFilter != null) {
        FilterBottomSheet(
            activeFilter = activeFilter!!,
            selectedStatus = selectedStatus,
            selectedType = selectedType,
            selectedDate = selectedTimeSpan,
            onTypeSelected = {
                onTypeSelected(it)
                activeFilter = null
            },
            onStatusSelected = {
                onStatusSelected(it)
                activeFilter = null
            },
            onDateSelected = {
                onDateSelected(it)
                activeFilter = null
            },
            status = status,
            types = type,
            onDismiss = {
                activeFilter = null
            },
            onApply = onApply
        ) {
            when(activeFilter){
                "Status" -> onStatusSelected(null)
                "Type" -> onTypeSelected(null)
                "Date" -> onDateSelected(null)
                else -> {}
            }
        }
    }
}