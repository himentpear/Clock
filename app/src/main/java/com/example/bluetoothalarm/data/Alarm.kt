package com.example.bluetoothalarm.data

import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
/**
 * Represents an alarm.
 *
 * @property id Unique identifier for the alarm.
 * @property hour The hour of the alarm (0-23).
 * @property minute The minute of the alarm (0-59).
 * @property name A label or name for the alarm.
 * @property soundUri The URI of the alarm sound.
 * @property isEnabled Whether the alarm is currently active.
 * @property isRecurring Whether the alarm repeats.
 * @property recurringDays The set of days on which the alarm repeats.
 */
data class Alarm(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val name: String,
    val soundUri: String,
    val isEnabled: Boolean,
    val isRecurring: Boolean,
    val recurringDays: Set<DayOfWeek>
)
