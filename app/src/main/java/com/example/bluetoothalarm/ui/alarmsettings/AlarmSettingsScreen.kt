package com.example.bluetoothalarm.ui.alarmsettings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSettingsScreen(
    alarmName: String,
    onAlarmNameChange: (String) -> Unit,
    isRecurring: Boolean,
    onIsRecurringChange: (Boolean) -> Unit,
    selectedDays: Set<DayOfWeek>,
    onDaySelected: (DayOfWeek) -> Unit,
    timePickerState: TimePickerState,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Set Alarm") },
                actions = {
                    TextButton(onClick = onSave) {
                        Text("Save")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Filled.Close, contentDescription = "Cancel")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimePicker(state = timePickerState)

            OutlinedTextField(
                value = alarmName,
                onValueChange = onAlarmNameChange,
                label = { Text("Alarm Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Recurring")
                Switch(
                    checked = isRecurring,
                    onCheckedChange = onIsRecurringChange
                )
            }

            if (isRecurring) {
                DaySelector(
                    selectedDays = selectedDays,
                    onDaySelected = onDaySelected
                )
            }
        }
    }
}

@Composable
fun DaySelector(
    selectedDays: Set<DayOfWeek>,
    onDaySelected: (DayOfWeek) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DayOfWeek.values().forEach { day ->
            FilledTonalButton(
                onClick = { onDaySelected(day) },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (selectedDays.contains(day)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(day.name.first().toString())
            }
        }
    }
}
