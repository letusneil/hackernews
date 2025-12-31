package com.letusneil.shared.util

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun formatTimeAgo(timestamp: Long, clock: Clock = Clock.System): String {
    val now = clock.now()
    val eventTime = Instant.fromEpochSeconds(timestamp)
    val duration = now - eventTime

    return when {
        duration.inWholeSeconds < 60 -> "just now"
        duration.inWholeMinutes < 60 -> "${duration.inWholeMinutes}m ago"
        duration.inWholeHours < 24 -> "${duration.inWholeHours}h ago"
        else -> "${duration.inWholeDays}d ago"
    }
}