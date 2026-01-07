package com.letusneil.hackernews

import android.app.Application
import com.letusneil.shared.di.sharedModule
import com.letusneil.shared.util.initKmpLogging
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HackernewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@HackernewsApplication)
            modules(sharedModule)
        }

        if (BuildConfig.DEBUG) {
            initKmpLogging()
        }
    }
}