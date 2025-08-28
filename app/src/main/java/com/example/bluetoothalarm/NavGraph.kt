package com.example.bluetoothalarm

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothalarm.ui.alarmsettings.AlarmSettingsScreen
import com.example.bluetoothalarm.ui.alarmsettings.AlarmSettingsViewModel
import com.example.bluetoothalarm.ui.mainscreen.MainScreen
import com.example.bluetoothalarm.ui.mainscreen.MainViewModel
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel(factory = factory)
    val alarms by mainViewModel.alarms.collectAsState()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                alarms = alarms,
                onAddAlarmClick = { navController.navigate("settings") },
                onAlarmEnabledChange = mainViewModel::onAlarmEnabledChange
            )
        }
        composable("settings") {
            val alarmSettingsViewModel: AlarmSettingsViewModel = viewModel(factory = factory)
            var alarmName by remember { mutableStateOf("") }
            var isRecurring by remember { mutableStateOf(false) }
            var selectedDays by remember { mutableStateOf(emptySet<DayOfWeek>()) }
            val timePickerState = rememberTimePickerState()

            AlarmSettingsScreen(
                alarmName = alarmName,
                onAlarmNameChange = { alarmName = it },
                isRecurring = isRecurring,
                onIsRecurringChange = { isRecurring = it },
                selectedDays = selectedDays,
                onDaySelected = { day ->
                    selectedDays = if (selectedDays.contains(day)) {
                        selectedDays - day
                    } else {
                        selectedDays + day
                    }
                },
                timePickerState = timePickerState,
                onSave = {
                    alarmSettingsViewModel.saveAlarm(
                        hour = timePickerState.hour,
                        minute = timePickerState.minute,
                        name = alarmName,
                        isRecurring = isRecurring,
                        recurringDays = selectedDays
                    )
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
