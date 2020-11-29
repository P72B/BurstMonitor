package de.p72b.burstpooltracker.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.p72b.burstpooltracker.util.Utils
import de.p72b.burstpooltracker.room.Miner
import java.util.Calendar

class MinerViewModel(private val repository: MinerRepository) :
    ViewModel() {

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