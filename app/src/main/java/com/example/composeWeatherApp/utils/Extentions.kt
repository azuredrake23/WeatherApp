package com.example.composeWeatherApp.utils

import android.os.Build
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Extentions {
    fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
        clear()
        addAll(newList)
    }

    fun String.toTimeRange(): TimesOfDay {
        val hour: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && this != "") {
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val localDateTime = LocalDateTime.parse(this, pattern)
            localDateTime.hour
        } else 0

        return when (hour) {
            in 0..6 -> {
                TimesOfDay.Night
            }

            in 6..12 -> {
                TimesOfDay.Morning
            }

            in 12..18 -> {
                TimesOfDay.Day
            }

            in 18..23 -> {
                TimesOfDay.Evening
            }

            else -> {
                TimesOfDay.Day
            }
        }
    }
}