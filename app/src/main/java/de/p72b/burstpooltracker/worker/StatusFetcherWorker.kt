package de.p72b.burstpooltracker.worker

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import de.p72b.burstpooltracker.main.MinerRepository
import de.p72b.burstpooltracker.util.Utils
import de.p72b.burstpooltracker.http.MinerPage
import de.p72b.burstpooltracker.http.WebService
import de.p72b.burstpooltracker.settings.ADDRESS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyWorkerFactory(
    private val webService: WebService,
    private val repository: MinerRepository) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return StatusFetcherWorker(webService, repository, appContext, workerParameters)
    }
}

class StatusFetcherWorker(
    private val webService: WebService,
    private val repository: MinerRepository,
    private val appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val minerPageResponse = getMinerPage()
        val address = PreferenceManager.getDefaultSharedPreferences(appContext).getString(appContext.getString(ADDRESS), "") ?: ""
        val foundPoiMiner = Utils.minerFromPage(minerPageResponse, address)
        if (foundPoiMiner != null) {
            repository.getLatestEntryFor(address)?.let {
                foundPoiMiner.delta_x = foundPoiMiner.credit - it.credit
                foundPoiMiner.delta_y = foundPoiMiner.timeMilliseconds - it.timeMilliseconds
                foundPoiMiner.pitch = foundPoiMiner.delta_x / (foundPoiMiner.delta_y / 86_400_000.0)
            }
            if (foundPoiMiner.delta_y == 0L || foundPoiMiner.delta_y > 60_000) {
                repository.insert(foundPoiMiner)
                Log.d("p72b", "poi miner found and added to db!")
            }
        } else {
            Log.d("p72b", "poi miner NOT found!")
            // TODO Upps miner not found! Alarm!
        }

        return Result.success()
    }

    private fun getMinerPage(): MinerPage {
        return webService.getMiners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .blockingFirst()
    }
}