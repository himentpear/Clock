package com.example.bluetoothalarm.ui.alarmsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothalarm.data.Alarm
import com.example.bluetoothalarm.repository.AlarmRepository
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import kotlin.random.Random

class AlarmSettingsViewModel(
    private val repository: AlarmRepository
) : ViewModel() {

    fun saveAlarm(
        hour: Int,
        minute: Int,
        name: String,
        isRecurring: Boolean,
        recurringDays: Set<DayOfWeek>,
        soundUri: String
    ) {
        viewModelScope.launch {
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
            repository.addAlarm(alarm)
        }
    }
}
