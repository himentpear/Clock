package com.example.bluetoothalarm.ui.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bluetoothalarm.data.Alarm
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AlarmItem(
    alarm: Alarm,
    onEnabledChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Switch(
                checked = alarm.isEnabled,
                onCheckedChange = onEnabledChange
            )
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Alarm"
                )
            }
        }
    }
}
