package de.p72b.burstpooltracker.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MinerDao {

    @Query("SELECT * from miner ORDER BY time_milliseconds DESC")
    fun getAll(): LiveData<List<Miner>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(miner: Miner)

    @Query("DELETE from miner")
    fun deleteAll()

    @Query("SELECT * from miner WHERE burst_id = :address ORDER BY time_milliseconds DESC LIMIT 1")
    fun getLatestEntryFor(address: String): Miner?
}