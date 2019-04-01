package de.p72b.burstpooltracker

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import de.p72b.burstpooltracker.koin.appModule
import io.fabric.sdk.android.Fabric
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
        initTracking()
        initKoin()
        initWorkManager()
    }

    private fun initTracking() {
        val shouldCrashReport = !Preferences.readBooleanFromPreferences(Preferences.OPT_OUT_CRASHLYTICS)
        val shouldAnalytics = !Preferences.readBooleanFromPreferences(Preferences.OPT_OUT_ANALYTICS)
        if (shouldCrashReport) {
            Fabric.with(this, Crashlytics())
        }
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(shouldAnalytics)
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(appModule)  }
    }

    private fun initWorkManager() {
        val configuration = Configuration.Builder()
            .setMinimumLoggingLevel(Log.VERBOSE)
            .build()
        WorkManager.initialize(this, configuration)
    }
}