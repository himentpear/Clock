package com.example.bluetoothalarm.alarm

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build

object AudioHelper {

    @SuppressLint("MissingPermission")
    fun isBluetoothHeadsetConnected(context: Context): Boolean {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter ?: return false

        // BLUETOOTH_CONNECT permission is required for this on API 31+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return bluetoothManager.adapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothProfile.STATE_CONNECTED
        } else {
            @Suppress("DEPRECATION")
            return bluetoothAdapter.isEnabled &&
                    BluetoothProfile.A2DP.let { bluetoothAdapter.getProfileConnectionState(it) } == BluetoothProfile.STATE_CONNECTED
        }
    }

    fun isWiredHeadsetConnected(context: Context): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        for (deviceInfo in audioDevices) {
            if (deviceInfo.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES || deviceInfo.type == AudioDeviceInfo.TYPE_WIRED_HEADSET) {
                return true
            }
        }
        return false
    }

    fun isHeadphonesConnected(context: Context): Boolean {
        return isBluetoothHeadsetConnected(context) || isWiredHeadsetConnected(context)
    }
}
