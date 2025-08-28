package com.example.bluetoothalarm.ui.alarmsettings

import com.example.bluetoothalarm.alarm.AlarmScheduler
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AlarmSettingsViewModelTest {

    private val alarmScheduler: AlarmScheduler = mock()
    private val viewModel = AlarmSettingsViewModel(alarmScheduler)

    @Test
    fun `when save alarm, schedule is called`() {
        viewModel.saveAlarm(8, 30, "Test", false, emptySet())
        verify(alarmScheduler).schedule(any())
    }
}
