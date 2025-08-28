package com.example.bluetoothalarm.repository

import com.example.bluetoothalarm.alarm.AlarmScheduler
import com.example.bluetoothalarm.data.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek

class InMemoryAlarmRepository(
    private val alarmScheduler: AlarmScheduler
) : AlarmRepository {

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())

    init {
        // Initialize with sample data
        _alarms.value = listOf(
            Alarm(1, 8, 30, "Wake up", "", true, false, emptySet()),
            Alarm(2, 9, 0, "Meeting", "", true, true, setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)),
            Alarm(3, 22, 0, "Go to bed", "", false, false, emptySet())
        )
    }

    override fun getAlarms(): Flow<List<Alarm>> = _alarms.asStateFlow()

    override suspend fun addAlarm(alarm: Alarm) {
        _alarms.update { currentAlarms ->
            currentAlarms + alarm
        }
        alarmScheduler.schedule(alarm)
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        _alarms.update { currentAlarms ->
            currentAlarms.map {
                if (it.id == alarm.id) alarm else it
            }
        }
        if (alarm.isEnabled) {
            alarmScheduler.schedule(alarm)
        } else {
            alarmScheduler.cancel(alarm)
        }
    }
}
