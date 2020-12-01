package de.p72b.burstpooltracker.main.usecase

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import de.p72b.burstpooltracker.main.MinerRepository
import de.p72b.burstpooltracker.room.Miner
import de.p72b.burstpooltracker.settings.ADDRESS

class ObserveLatestMinerUserCase(
    private val repository: MinerRepository,
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) {
    operator fun invoke(): LiveData<Miner?> {
        val address = sharedPreferences.getString(context.getString(ADDRESS), "") ?: ""
        return repository.observeLatestMiner(address)
    }
}