# Bluetooth Alarm App

This is a simple Android application that allows users to set alarms that will only play through a connected Bluetooth or wired headset. This is useful for users who want to wake up without disturbing others.

## Features

*   Set, edit, and delete alarms.
*   Alarms only play through a connected Bluetooth or wired headset.
*   If no headset is connected, the alarm will not play through the speaker.
*   Recurring alarms.
*   Customizable alarm names.

## Building and Running

To build and run this application, you will need Android Studio.

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    ```
2.  **Open the project in Android Studio:**
    *   Open Android Studio.
    *   Click on "Open" and navigate to the cloned repository folder.
    *   Select the folder and click "OK".
3.  **Build the project:**
    *   Wait for Android Studio to sync the Gradle files.
    *   Click on "Build" > "Make Project" or use the shortcut `Ctrl+F9` (`Cmd+F9` on Mac).
4.  **Run the application:**
    *   Connect an Android device or start an emulator.
    *   Click on "Run" > "Run 'app'" or use the shortcut `Shift+F10` (`Ctrl+R` on Mac).

## How it Works

The application uses the Android `AlarmManager` to schedule alarms. When an alarm is triggered, a `BroadcastReceiver` starts a foreground `Service`. This service then checks for a connected Bluetooth or wired headset using `AudioManager` and `BluetoothManager`. If a headset is connected, the alarm sound is played using `MediaPlayer`. If no headset is connected, the alarm remains silent.
