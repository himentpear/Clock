package com.example.bluetoothalarm.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothalarm.data.Alarm
import com.example.bluetoothalarm.repository.AlarmRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: AlarmRepository
) : ViewModel() {

    val alarms: StateFlow<List<Alarm>> = repository.getAlarms()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onAlarmEnabledChange(alarm: Alarm, isEnabled: Boolean) {
        viewModelScope.launch {
            repository.updateAlarm(alarm.copy(isEnabled = isEnabled))
        }
    }
}
