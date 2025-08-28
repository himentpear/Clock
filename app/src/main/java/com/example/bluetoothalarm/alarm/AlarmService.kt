package com.example.bluetoothalarm.alarm

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.bluetoothalarm.R

class AlarmService : Service() {

    companion object {
        const val ACTION_STOP = "STOP_ALARM"
    }

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var audioManager: AudioManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP) {
            stopSelf()
            return START_NOT_STICKY
        }

        val alarmName = intent?.getStringExtra("ALARM_NAME") ?: "Alarm"
        val soundUriString = intent?.getStringExtra("ALARM_SOUND_URI")

        createNotificationChannel()

        val stopIntent = Intent(this, AlarmService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "alarm_channel")
            .setContentTitle("Alarm")
            .setContentText(alarmName)
            .setSmallIcon(R.drawable.ic_alarm)
            .addAction(R.drawable.ic_alarm, "Stop", stopPendingIntent)
            .build()

        startForeground(1, notification)

        // Check for headphones and play sound only if connected
        if (AudioHelper.isHeadphonesConnected(this)) {
            playSound(soundUriString)
        } else {
            // No headphones connected, so don't play sound.
            // Stop the service to release resources. The notification will remain.
            stopSelf()
        }

        return START_STICKY
    }

    private fun playSound(soundUriString: String?) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_ALARM)
            try {
                val soundUri: Uri? = if (soundUriString.isNullOrEmpty()) {
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                } else {
                    Uri.parse(soundUriString)
                }

                if (soundUri == null) {
                    // Fallback to notification sound if no default alarm or custom sound is set
                    val fallbackUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    setDataSource(applicationContext, fallbackUri)
                } else {
                    setDataSource(applicationContext, soundUri)
                }

                isLooping = true
                prepareAsync()
                setOnPreparedListener {
                    it.start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error
                stopSelf()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alarm Channel"
            val descriptionText = "Channel for alarm notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("alarm_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
