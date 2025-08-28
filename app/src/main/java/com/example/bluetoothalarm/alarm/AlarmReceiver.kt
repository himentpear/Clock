package com.example.bluetoothalarm.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.bluetoothalarm.AlarmActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmName = intent.getStringExtra("ALARM_NAME")
        val soundUri = intent.getStringExtra("ALARM_SOUND_URI")

        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
            putExtra("ALARM_NAME", alarmName)
            putExtra("ALARM_SOUND_URI", soundUri)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(activityIntent)
    }
}
