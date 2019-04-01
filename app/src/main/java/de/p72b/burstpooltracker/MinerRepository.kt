package de.p72b.burstpooltracker

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import de.p72b.burstpooltracker.room.Miner
import de.p72b.burstpooltracker.room.MinerDao
import de.p72b.burstpooltracker.room.RoomMinerDatabase
import org.koin.core.KoinComponent

class MinerRepository : KoinComponent {
    private val minerDao: MinerDao
    private val allMiner: LiveData<List<Miner>>

    init {
        val db = RoomMinerDatabase.getInstance(App.sInstance)
        minerDao = db?.minerDao()!!
        allMiner = minerDao.getAll()
    }

    fun getAllMiners(): LiveData<List<Miner>> {
        return allMiner
    }

    fun getLatestEntryFor(address: String): Miner? {
        return minerDao.getLatestEntryFor(address)
    }

    fun insert(miner: Miner) {
        InsertAsyncTask(minerDao).execute(miner)
    }

    class InsertAsyncTask internal constructor(private val asyncTaskDao: MinerDao) : AsyncTask<Miner, Void, Void>() {
        override fun doInBackground(vararg params: Miner): Void? {
            asyncTaskDao.insert(params[0])
            return null
        }
    }

}