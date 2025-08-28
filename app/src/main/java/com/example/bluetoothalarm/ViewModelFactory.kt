package com.example.bluetoothalarm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bluetoothalarm.alarm.AndroidAlarmScheduler
import com.example.bluetoothalarm.repository.AlarmRepository
import com.example.bluetoothalarm.repository.InMemoryAlarmRepository
import com.example.bluetoothalarm.ui.alarmsettings.AlarmSettingsViewModel
import com.example.bluetoothalarm.ui.mainscreen.MainViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    // Create a single instance of the repository
    private val alarmRepository: AlarmRepository by lazy {
        InMemoryAlarmRepository(AndroidAlarmScheduler(context.applicationContext))
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(alarmRepository) as T
        }
        if (modelClass.isAssignableFrom(AlarmSettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlarmSettingsViewModel(alarmRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
