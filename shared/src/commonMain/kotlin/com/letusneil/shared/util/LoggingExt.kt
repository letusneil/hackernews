package com.letusneil.shared.util

// commonMain/Platform.kt (or AppInit.kt)
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initKmpLogging() {
    // Only initialize if you haven't already to avoid duplicate logs
    // In a real app, you might wrap this in a 'isDebug' check
    Napier.base(DebugAntilog())
}