package com.example.bluetoothalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bluetoothalarm.ui.theme.BluetoothAlarmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetoothAlarmTheme {
                NavGraph()
            }
        }
    }
}
