package de.p72b.burstpooltracker

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import de.p72b.burstpooltracker.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    companion object {
        lateinit var sInstance: App
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        initKoin()
        initWorkManager()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            androidLogger()

            modules(appModule)
        }
    }

    private fun initWorkManager() {
        val configuration = Configuration.Builder()
            .setMinimumLoggingLevel(Log.VERBOSE)
            .build()
        WorkManager.initialize(this, configuration)
    }
}