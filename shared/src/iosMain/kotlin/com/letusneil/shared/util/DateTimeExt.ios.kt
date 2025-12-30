package com.letusneil.shared.util

import platform.Foundation.*

actual fun formatTimeAgo(epochSeconds: Long): String {
    val date = NSDate.dateWithTimeIntervalSince1970(epochSeconds.toDouble())
    val formatter = NSRelativeDateTimeFormatter()
    formatter.unitsStyle = NSRelativeDateTimeFormatterUnitsStyleFull

    return formatter.localizedStringForDate(date, NSDate.now())
}