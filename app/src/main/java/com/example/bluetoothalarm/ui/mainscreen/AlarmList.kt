package com.example.bluetoothalarm.ui.mainscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bluetoothalarm.data.Alarm

@Composable
fun AlarmList(
    alarms: List<Alarm>,
    onAlarmEnabledChange: (Alarm, Boolean) -> Unit,
    onDeleteClick: (Alarm) -> Unit,
    onItemClick: (Alarm) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(alarms) { alarm ->
            AlarmItem(
                alarm = alarm,
                onEnabledChange = { isEnabled ->
                    onAlarmEnabledChange(alarm, isEnabled)
                },
                onDeleteClick = {
                    onDeleteClick(alarm)
                },
                onClick = {
                    onItemClick(alarm)
                }
            )
        }
    }
}
