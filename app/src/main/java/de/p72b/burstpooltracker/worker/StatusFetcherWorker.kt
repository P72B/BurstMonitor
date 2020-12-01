package de.p72b.burstpooltracker.worker

import android.content.Context
import androidx.room.ColumnInfo
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import de.p72b.burstpooltracker.main.MinerRepository
import de.p72b.burstpooltracker.http.WebService
import de.p72b.burstpooltracker.http.filter
import de.p72b.burstpooltracker.main.usecase.GetLatestMinerStatusUserCase
import de.p72b.burstpooltracker.room.Miner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyWorkerFactory(
    private val address: String,
    private val webService: WebService,
    private val repository: MinerRepository,
    private val getLatestMinerStatusUserCase: GetLatestMinerStatusUserCase
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return StatusFetcherWorker(
            address,
            webService,
            repository,
            getLatestMinerStatusUserCase,
            appContext,
            workerParameters
        )
    }
}

class StatusFetcherWorker(
    private val address: String,
    private val webService: WebService,
    private val repository: MinerRepository,
    private val getLatestMinerStatusUserCase: GetLatestMinerStatusUserCase,
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            val minerPage = withContext(Dispatchers.IO) {
                webService.getMiners()
            }
            minerPage.filter(address)?.let {
                processItem(it, getLatestMinerStatusUserCase())
            }
        }
        return Result.success()
    }

    private fun processItem(fetchedMiner: Miner, previousMiner: Miner?) {
        previousMiner?.let {
            fetchedMiner.delta_credit = fetchedMiner.credit - it.credit
            fetchedMiner.delta_time_milliseconds = fetchedMiner.timeMilliseconds - it.timeMilliseconds
            fetchedMiner.delta_historical_share = fetchedMiner.historicalShare - it.historicalShare
            fetchedMiner.delta_plot_size = fetchedMiner.plotSize - it.plotSize
            fetchedMiner.pitch = fetchedMiner.delta_credit / (fetchedMiner.delta_time_milliseconds / 86_400_000.0)
        }
        if (previousMiner == null || fetchedMiner.delta_time_milliseconds > 60_000) {
            repository.insert(fetchedMiner)
        }
    }
}