package com.example.bluetoothalarm.ui.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bluetoothalarm.data.Alarm
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AlarmItem(
    alarm: Alarm,
    onEnabledChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = String.format("%02d:%02d", alarm.hour, alarm.minute),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = alarm.name,
                style = MaterialTheme.typography.bodyLarge
            )
            if (alarm.isRecurring) {
                Text(
                    text = alarm.recurringDays.joinToString { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Switch(
            checked = alarm.isEnabled,
            onCheckedChange = onEnabledChange
        )
    }
}
