package com.example.bluetoothalarm.ui.alarmsettings

import androidx.lifecycle.ViewModel
import com.example.bluetoothalarm.alarm.AlarmScheduler
import com.example.bluetoothalarm.data.Alarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.DayOfWeek
import kotlin.random.Random

class AlarmSettingsViewModel(
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    private val _alarm = MutableStateFlow<Alarm?>(null)
    val alarm: StateFlow<Alarm?> = _alarm

    fun setAlarm(alarm: Alarm?) {
        _alarm.value = alarm
    }

    fun saveAlarm(
        hour: Int,
        minute: Int,
        name: String,
        isRecurring: Boolean,
        recurringDays: Set<DayOfWeek>,
        soundUri: String
    ) {
        val alarm = Alarm(
            id = Random.nextInt(),
            hour = hour,
            minute = minute,
            name = name,
            soundUri = soundUri,
            isEnabled = true,
            isRecurring = isRecurring,
            recurringDays = recurringDays
        )
        alarmScheduler.schedule(alarm)
    }
}
