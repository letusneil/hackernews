package com.letusneil.shared.util

import android.text.format.DateUtils

actual fun formatTimeAgo(epochSeconds: Long): String {
    return DateUtils.getRelativeTimeSpanString(
        epochSeconds * 1000, // Android uses milliseconds
        System.currentTimeMillis(),
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}