package com.example.bluetoothalarm.ui.alarmsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothalarm.data.Alarm
import com.example.bluetoothalarm.repository.AlarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import kotlin.random.Random

class AlarmSettingsViewModel(
    private val repository: AlarmRepository
) : ViewModel() {

    private val _alarmState = MutableStateFlow<Alarm?>(null)
    val alarmState = _alarmState.asStateFlow()

    fun loadAlarm(id: Int) {
        viewModelScope.launch {
            _alarmState.value = repository.getAlarmById(id)
        }
    }

    fun saveOrUpdateAlarm(
        id: Int?,
        hour: Int,
        minute: Int,
        name: String,
        isRecurring: Boolean,
        recurringDays: Set<DayOfWeek>,
        soundUri: String
    ) {
        viewModelScope.launch {
            val alarm = Alarm(
                id = id ?: Random.nextInt(),
                hour = hour,
                minute = minute,
                name = name,
                soundUri = soundUri,
                isEnabled = true,
                isRecurring = isRecurring,
                recurringDays = recurringDays
            )
            if (id == null) {
                repository.addAlarm(alarm)
            } else {
                repository.updateAlarm(alarm)
            }
        }
    }
}
