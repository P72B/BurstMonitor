package de.p72b.burstpooltracker.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.p72b.burstpooltracker.MinerRepository
import de.p72b.burstpooltracker.Utils
import de.p72b.burstpooltracker.room.Miner
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class MinerViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    private val repository: MinerRepository by inject()

    fun insert(miner: Miner) {
        repository.insert(miner)
    }

    fun getMiners(isDesc: Boolean = false): LiveData<List<Miner>> {
        val timeFilter = Utils.filterTime()
        return if (timeFilter == 0L) {
            repository.getAllMiners(isDesc)
        } else {
            val age = Calendar.getInstance().timeInMillis - timeFilter
            repository.filter(isDesc, age)
        }
    }
}