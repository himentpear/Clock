package com.example.bluetoothalarm.repository

import com.example.bluetoothalarm.data.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAlarms(): Flow<List<Alarm>>
    suspend fun addAlarm(alarm: Alarm)
    suspend fun updateAlarm(alarm: Alarm)
}
