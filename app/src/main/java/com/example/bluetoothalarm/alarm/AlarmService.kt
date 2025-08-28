package com.example.bluetoothalarm.alarm

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder

class AlarmService : Service() {

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
        val soundUriString = intent?.getStringExtra("ALARM_SOUND_URI")

        if (AudioHelper.isHeadphonesConnected(this)) {
            playSound(soundUriString)
        } else {
            stopSelf()
        }

        return START_STICKY
    }

    private fun playSound(soundUriString: String?) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                val soundUri: Uri? = if (soundUriString.isNullOrEmpty()) {
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                } else {
                    Uri.parse(soundUriString)
                }

                if (soundUri == null) {
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
}
