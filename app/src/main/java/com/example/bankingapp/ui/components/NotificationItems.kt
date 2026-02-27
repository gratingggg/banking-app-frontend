package com.example.bankingapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.models.notifications.NotificationResponseDto
import com.example.bankingapp.ui.theme.lightCoralPink
import com.example.bankingapp.utils.Constants
import com.example.bankingapp.utils.NotificationStatus
import java.time.format.DateTimeFormatter

@Composable
fun NotificationItem(
    notification: NotificationResponseDto,
    modifier: Modifier = Modifier,
    onNotificationClick: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(onClick = {
                onNotificationClick(notification.notificationId)
            }),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.notificationStatus == NotificationStatus.READ) {
                Color(
                    0xFF2A2A2A
                )
            } else lightCoralPink
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(0.8f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = notification.notificationType.name.lowercase()
                        .replaceFirstChar { it.uppercase() },
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                    maxLines = Int.MAX_VALUE,
                    overflow = TextOverflow.Clip
                )

                Text(
                    text = notification.message,
                    fontSize = 14.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = notification.date.format(DateTimeFormatter.ofPattern(Constants.LOCAL_DATE_TIME_PATTERN)),
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}