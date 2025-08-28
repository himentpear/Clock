package com.example.bluetoothalarm

import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bluetoothalarm.ui.alarmsettings.AlarmSettingsScreen
import com.example.bluetoothalarm.ui.alarmsettings.AlarmSettingsViewModel
import com.example.bluetoothalarm.ui.mainscreen.MainScreen
import com.example.bluetoothalarm.ui.mainscreen.MainViewModel
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    permissionsGranted: Boolean
) {
    val context = LocalContext.current
    val factory = remember(context) {
        ViewModelFactory(context)
    }
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel(factory = factory)
    val alarms by mainViewModel.alarms.collectAsState()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                alarms = alarms,
                onAddAlarmClick = { navController.navigate("settings") },
                onItemClick = { alarm ->
                    navController.navigate("settings?alarmId=${alarm.id}")
                },
                onAlarmEnabledChange = mainViewModel::onAlarmEnabledChange,
                onDeleteClick = mainViewModel::deleteAlarm,
                permissionsGranted = permissionsGranted
            )
        }
        composable(
            route = "settings?alarmId={alarmId}",
            arguments = listOf(navArgument("alarmId") {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val alarmIdStr = backStackEntry.arguments?.getString("alarmId")
            val alarmId = alarmIdStr?.toIntOrNull()

            val alarmSettingsViewModel: AlarmSettingsViewModel = viewModel(factory = factory)

            LaunchedEffect(alarmId) {
                if (alarmId != null) {
                    alarmSettingsViewModel.loadAlarm(alarmId)
                }
            }

            val loadedAlarm by alarmSettingsViewModel.alarmState.collectAsState()

            var alarmName by remember(loadedAlarm) { mutableStateOf(loadedAlarm?.name ?: "") }
            var isRecurring by remember(loadedAlarm) { mutableStateOf(loadedAlarm?.isRecurring ?: false) }
            var selectedDays by remember(loadedAlarm) { mutableStateOf(loadedAlarm?.recurringDays ?: emptySet()) }
            val timePickerState = rememberTimePickerState(
                initialHour = loadedAlarm?.hour ?: 0,
                initialMinute = loadedAlarm?.minute ?: 0
            )
            var selectedRingtoneUri by remember(loadedAlarm) { mutableStateOf(loadedAlarm?.soundUri?.let { Uri.parse(it) }) }

            val ringtoneName by remember(selectedRingtoneUri) {
                derivedStateOf {
                    val defaultTitle = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))?.getTitle(context) ?: "Default"
                    if (selectedRingtoneUri != null) {
                        RingtoneManager.getRingtone(context, selectedRingtoneUri).getTitle(context)
                    } else {
                        defaultTitle
                    }
                }
            }

            val ringtonePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val uri = result.data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                        selectedRingtoneUri = uri
                    }
                }
            )

            AlarmSettingsScreen(
                alarmName = alarmName,
                onAlarmNameChange = { alarmName = it },
                isRecurring = isRecurring,
                onIsRecurringChange = { isRecurring = it },
                selectedDays = selectedDays,
                onDaySelected = { day ->
                    selectedDays = if (selectedDays.contains(day)) selectedDays - day else selectedDays + day
                },
                ringtoneName = ringtoneName,
                onSelectRingtoneClick = {
                    val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
                    ringtonePickerLauncher.launch(intent)
                },
                timePickerState = timePickerState,
                onSave = {
                    alarmSettingsViewModel.saveOrUpdateAlarm(
                        id = alarmId,
                        hour = timePickerState.hour,
                        minute = timePickerState.minute,
                        name = alarmName,
                        isRecurring = isRecurring,
                        recurringDays = selectedDays,
                        soundUri = selectedRingtoneUri?.toString() ?: ""
                    )
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
