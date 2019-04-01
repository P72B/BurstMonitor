package de.p72b.burstpooltracker.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.p72b.burstpooltracker.MinerRepository
import de.p72b.burstpooltracker.room.Miner
import org.koin.core.KoinComponent
import org.koin.core.inject

class MinerViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    private val repository: MinerRepository by inject()
    val allMiners: LiveData<List<Miner>>

    init {
        allMiners = repository.getAllMiners()
    }

    fun insert(miner: Miner) {
        repository.insert(miner)
    }
}