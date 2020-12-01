package de.p72b.burstpooltracker

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.work.Configuration
import de.p72b.burstpooltracker.http.WebService
import de.p72b.burstpooltracker.main.MinerRepository
import de.p72b.burstpooltracker.main.usecase.GetLatestMinerStatusUserCase
import de.p72b.burstpooltracker.settings.ADDRESS
import de.p72b.burstpooltracker.worker.MyWorkerFactory
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application(), Configuration.Provider {

    companion object {
        lateinit var sInstance: App
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)

            modules(appModule)
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val address = PreferenceManager.getDefaultSharedPreferences(sInstance).getString(sInstance.getString(ADDRESS), "") ?: ""
        val webService: WebService by inject()
        val minerRepository: MinerRepository by inject()
        val getLatestMinerStatusUserCase: GetLatestMinerStatusUserCase by inject()
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(MyWorkerFactory(address, webService, minerRepository, getLatestMinerStatusUserCase))
            .build()
    }
}