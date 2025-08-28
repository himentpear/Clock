package com.example.bluetoothalarm.repository

import android.content.Context
import com.example.bluetoothalarm.alarm.AlarmScheduler
import com.example.bluetoothalarm.data.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

class JsonFileAlarmRepository(
    private val context: Context,
    private val alarmScheduler: AlarmScheduler
) : AlarmRepository {

    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
    private val file = File(context.filesDir, "alarms.json")

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())

    init {
        try {
            if (file.exists()) {
                val fileContent = file.readText()
                if (fileContent.isNotBlank()) {
                    _alarms.value = Json.decodeFromString<List<Alarm>>(fileContent)
                }
            }
        } catch (e: IOException) {
            // Handle error reading file
            e.printStackTrace()
        }
    }

    private fun writeToFile() {
        try {
            val jsonString = json.encodeToString(_alarms.value)
            file.writeText(jsonString)
        } catch (e: IOException) {
            // Handle error writing file
            e.printStackTrace()
        }
    }

    override fun getAlarms(): Flow<List<Alarm>> = _alarms.asStateFlow()

    override suspend fun addAlarm(alarm: Alarm) {
        _alarms.update { currentAlarms ->
            currentAlarms + alarm
        }
        writeToFile()
        alarmScheduler.schedule(alarm)
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        _alarms.update { currentAlarms ->
            currentAlarms.map {
                if (it.id == alarm.id) alarm else it
            }
        }
        writeToFile()
        if (alarm.isEnabled) {
            alarmScheduler.schedule(alarm)
        } else {
            alarmScheduler.cancel(alarm)
        }
    }
}
