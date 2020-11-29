package de.p72b.burstpooltracker.main

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import de.p72b.burstpooltracker.App
import de.p72b.burstpooltracker.room.Miner
import de.p72b.burstpooltracker.room.MinerDao
import de.p72b.burstpooltracker.room.RoomMinerDatabase

class MinerRepository {
    private val minerDao: MinerDao
    private val allMiner: LiveData<List<Miner>>
    private val getAllDesc: LiveData<List<Miner>>

    init {
        val db = RoomMinerDatabase.getInstance(App.sInstance)
        minerDao = db?.minerDao()!!
        allMiner = minerDao.getAll()
        getAllDesc = minerDao.getAllDesc()
    }

    fun getAllMiners(isDesc: Boolean = false): LiveData<List<Miner>> {
        return if (isDesc) {
            getAllDesc
        } else {
            allMiner
        }
    }

    fun filter(isDesc: Boolean = false, value: Long): LiveData<List<Miner>> {
        return if (isDesc) {
            minerDao.filterDesc(value)
        } else {
            minerDao.filter(value)
        }
    }

    fun getLatestEntryFor(address: String): Miner? {
        return minerDao.getLatestEntryFor(address)
    }

    fun insert(miner: Miner) {
        InsertAsyncTask(minerDao).execute(miner)
    }

    fun deleteWhereDeltaYSmallerAs(value: Long) {
        minerDao.deleteWhereDeltaYSmallerAs(value)
    }

    class InsertAsyncTask internal constructor(private val asyncTaskDao: MinerDao) : AsyncTask<Miner, Void, Void>() {
        override fun doInBackground(vararg params: Miner): Void? {
            asyncTaskDao.insert(params[0])
            return null
        }
    }

}