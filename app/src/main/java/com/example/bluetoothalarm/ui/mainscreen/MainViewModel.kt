package com.example.bluetoothalarm.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothalarm.alarm.AlarmScheduler
import com.example.bluetoothalarm.data.Alarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class MainViewModel(
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> = _alarms

    init {
        // Load sample data
        viewModelScope.launch {
            _alarms.value = listOf(
                Alarm(1, 8, 30, "Wake up", "", true, false, emptySet()),
                Alarm(2, 9, 0, "Meeting", "", true, true, setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)),
                Alarm(3, 22, 0, "Go to bed", "", false, false, emptySet())
            )
        }
    }

    fun onAlarmEnabledChange(alarm: Alarm, isEnabled: Boolean) {
        viewModelScope.launch {
            _alarms.value = _alarms.value.map {
                if (it.id == alarm.id) {
                    it.copy(isEnabled = isEnabled)
                } else {
                    it
                }
            }
        }
        if (isEnabled) {
            alarmScheduler.schedule(alarm.copy(isEnabled = true))
        } else {
            alarmScheduler.cancel(alarm)
        }
    }
}
