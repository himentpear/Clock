package com.example.bluetoothalarm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bluetoothalarm.alarm.AndroidAlarmScheduler
import com.example.bluetoothalarm.ui.alarmsettings.AlarmSettingsViewModel
import com.example.bluetoothalarm.ui.mainscreen.MainViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(AndroidAlarmScheduler(context)) as T
        }
        if (modelClass.isAssignableFrom(AlarmSettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlarmSettingsViewModel(AndroidAlarmScheduler(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
