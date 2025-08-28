package com.example.bluetoothalarm.alarm

import com.example.bluetoothalarm.data.Alarm

interface AlarmScheduler {
    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}
