package com.example.bluetoothalarm

import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetoothalarm.alarm.AlarmService
import com.example.bluetoothalarm.ui.theme.BluetoothAlarmTheme

class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        volumeControlStream = AudioManager.STREAM_ALARM

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Start the service to play the alarm sound
        val serviceIntent = Intent(this, AlarmService::class.java).apply {
            putExtra("ALARM_NAME", intent.getStringExtra("ALARM_NAME"))
            putExtra("ALARM_SOUND_URI", intent.getStringExtra("ALARM_SOUND_URI"))
        }
        startService(serviceIntent)

        setContent {
            BluetoothAlarmTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            // Stop the service and dismiss the activity
                            stopService(Intent(this@AlarmActivity, AlarmService::class.java))
                            finish()
                        },
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(text = "Stop", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}
