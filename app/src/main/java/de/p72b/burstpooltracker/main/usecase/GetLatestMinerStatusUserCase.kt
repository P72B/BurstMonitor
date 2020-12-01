package de.p72b.burstpooltracker.main.usecase

import android.content.Context
import android.content.SharedPreferences
import de.p72b.burstpooltracker.main.MinerRepository
import de.p72b.burstpooltracker.room.Miner
import de.p72b.burstpooltracker.settings.ADDRESS

class GetLatestMinerStatusUserCase(
    private val repository: MinerRepository,
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) {
    suspend operator fun invoke(): Miner? {
        val address = sharedPreferences.getString(context.getString(ADDRESS), "") ?: ""
        return repository.getLatestMiner(address)
    }
}