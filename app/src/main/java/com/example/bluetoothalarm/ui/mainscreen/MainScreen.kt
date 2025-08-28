package com.example.bluetoothalarm.ui.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetoothalarm.data.Alarm
import com.example.bluetoothalarm.ui.theme.BluetoothAlarmTheme
import java.time.DayOfWeek

@Composable
fun MainScreen(
    alarms: List<Alarm>,
    onAddAlarmClick: () -> Unit,
    onAlarmEnabledChange: (Alarm, Boolean) -> Unit,
    onDeleteClick: (Alarm) -> Unit,
    onItemClick: (Alarm) -> Unit,
    permissionsGranted: Boolean
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAlarmClick) {
                Icon(Icons.Filled.Add, contentDescription = "Add Alarm")
            }
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            if (!permissionsGranted) {
                Text(
                    text = "Warning: Required permissions for notifications and Bluetooth are not granted. The alarm may not function correctly.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
            AlarmList(
                alarms = alarms,
                onAlarmEnabledChange = onAlarmEnabledChange,
                onDeleteClick = onDeleteClick,
                onItemClick = onItemClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BluetoothAlarmTheme {
        val sampleAlarms = listOf(
            Alarm(1, 8, 30, "Wake up", "", true, false, emptySet()),
            Alarm(2, 9, 0, "Meeting", "", true, true, setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)),
            Alarm(3, 22, 0, "Go to bed", "", false, false, emptySet())
        )
        MainScreen(
            alarms = sampleAlarms,
            onAddAlarmClick = {},
            onAlarmEnabledChange = { _, _ -> },
            onDeleteClick = {},
            onItemClick = {},
            permissionsGranted = true
        )
    }
}
