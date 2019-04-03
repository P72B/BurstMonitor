package de.p72b.burstpooltracker

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.work.Configuration
import androidx.work.WorkManager
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import de.p72b.burstpooltracker.koin.appModule
import de.p72b.burstpooltracker.settings.CRASHLYTIC_TRACKING
import de.p72b.burstpooltracker.settings.FIREBASE_TRACKING
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
        val prefereces = PreferenceManager.getDefaultSharedPreferences(this)
        val shouldCrashReport = !prefereces.getBoolean(getString(FIREBASE_TRACKING),false)
        val shouldAnalytics = !prefereces.getBoolean(getString(CRASHLYTIC_TRACKING),false)
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