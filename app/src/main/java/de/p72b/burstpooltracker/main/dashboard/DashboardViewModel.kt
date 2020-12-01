package de.p72b.burstpooltracker.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import de.p72b.burstpooltracker.main.BaseMinerViewModel
import de.p72b.burstpooltracker.main.usecase.ObserveLatestMinerUserCase
import de.p72b.burstpooltracker.main.MinerRepository
import de.p72b.burstpooltracker.room.Miner

class DashboardViewModel(
    repository: MinerRepository,
    observeLatestMinerUserCase: ObserveLatestMinerUserCase
) : BaseMinerViewModel(repository) {

    val latestMiner: LiveData<Miner> = Transformations.switchMap(observeLatestMinerUserCase()) { miner ->
        miner?.let {
            applyValue(it)
        }
        observeLatestMinerUserCase()
    }

    private fun applyValue(miner: Miner) {

    }
}