package com.letusneil.shared.util

import kotlinx.datetime.Clock

//fun formatTimeAgo(epochSeconds: Long): String {
//    val now = Clock.System.now().epochSeconds
//    val diff = now - epochSeconds
//
//    return when {
//        diff < 60 -> "just now"
//        diff < 3600 -> "${diff / 60}m"
//        diff < 86400 -> "${diff / 3600}h"
//        diff < 2592000 -> "${diff / 86400}d"
//        else -> "${diff / 2592000}mo"
//    }
//}
expect fun formatTimeAgo(epochSeconds: Long): String