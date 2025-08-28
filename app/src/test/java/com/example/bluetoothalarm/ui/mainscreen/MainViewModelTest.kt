package com.example.bluetoothalarm.ui.mainscreen

import com.example.bluetoothalarm.alarm.AlarmScheduler
import com.example.bluetoothalarm.data.Alarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.DayOfWeek

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val alarmScheduler: AlarmScheduler = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(alarmScheduler)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when alarm enabled, schedule is called`() = runTest {
        val alarm = Alarm(1, 8, 30, "Test", "", false, false, emptySet())
        viewModel.onAlarmEnabledChange(alarm, true)
        verify(alarmScheduler).schedule(alarm.copy(isEnabled = true))
    }

    @Test
    fun `when alarm disabled, cancel is called`() = runTest {
        val alarm = Alarm(1, 8, 30, "Test", "", true, false, emptySet())
        viewModel.onAlarmEnabledChange(alarm, false)
        verify(alarmScheduler).cancel(alarm)
    }
}
