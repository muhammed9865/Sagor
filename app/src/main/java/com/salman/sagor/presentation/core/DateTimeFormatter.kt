package com.salman.sagor.presentation.core

import android.content.Context
import com.salman.sagor.R
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle
import java.util.Locale


/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/5/2024.
 */

fun LocalDateTime.formatToString(context: Context): String {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val isToday = date == today.date
    val isYesterday = date == today.date.minus(DatePeriod(days = 1))
    val hour = hour.toString().padStart(2, '0')
    val minute = minute.toString().padStart(2, '0')

    return when {
        isToday -> context.getString(R.string.today_at, hour, minute)
        isYesterday -> context.getString(R.string.yesterday_at, hour, minute)
        else -> {
            val day = dayOfMonth.toString().padStart(2, '0')
            val monthDisplay = month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            context.getString(R.string.date_at, day, monthDisplay, year, hour, minute)
        }
    }
}